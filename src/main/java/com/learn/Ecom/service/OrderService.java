package com.learn.Ecom.service;

import com.learn.Ecom.client.ProductClient;
import com.learn.Ecom.client.UserClient;
import com.learn.Ecom.repo.OrderItemRepository;
import com.learn.Ecom.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.learn.Ecom.model.Order;
import com.learn.Ecom.model.OrderItem;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    @Transactional
    public Order createOrder(Long userId, List<OrderItemRequest> itemRequests) {
        // Validate user exists (via User Service)
        UserClient.UserDTO userDto;
        try {
            userDto = userClient.getUserById(userId);
        } catch (Exception e) {
            throw new RuntimeException("User not found or service unavailable for id: " + userId);
        }

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatus(Order.OrderStatus.PENDING);
        order.setCurrency("INR");
        order.setOrderItems(new ArrayList<>());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // Process each order item
        for (OrderItemRequest itemRequest : itemRequests) {
            ProductClient.ProductDTO product;
            try {
                product = productClient.getProductById(itemRequest.getProductId());
            } catch (Exception e) {
                throw new RuntimeException(
                        "Product service unavailable or product not found with id: " + itemRequest.getProductId());
            }

            // Check stock availability (local check based on DTO, but real check happens in
            // reduceStock)
            if (product.stockQuantity() == null || product.stockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.name());
            }

            // Check if product is available
            if (product.productAvailable() == null || !product.productAvailable()) {
                throw new RuntimeException("Product is not available: " + product.name());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.id());
            orderItem.setProductNameSnapshot(product.name());
            orderItem.setUnitPrice(product.price());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.calculateSubtotal();

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(orderItem.getSubtotal());

            // Update product stock (Call Product Service)
            try {
                productClient.reduceStock(product.id(), itemRequest.getQuantity());
            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to reduce stock for product: " + product.name() + ". " + e.getMessage());
            }
        }

        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        // Save order (cascade will save order items)
        Order savedOrder = orderRepository.save(order);

        return savedOrder;
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        // If cancelling, restore stock
        if (order.getOrderStatus() != Order.OrderStatus.CANCELLED &&
                newStatus == Order.OrderStatus.CANCELLED) {
            restoreStock(order);
        }

        order.setOrderStatus(newStatus);
        return orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (order.getOrderStatus() == Order.OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled");
        }

        if (order.getOrderStatus() == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel a delivered order");
        }

        restoreStock(order);
        order.setOrderStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private void restoreStock(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            try {
                // Determine quantity to restore (negative reduction)
                // -item.getQuantity() -> if quantity is 5, pass -5. Logic: stock - (-5) = stock
                // + 5.
                productClient.reduceStock(item.getProductId(), -item.getQuantity());
            } catch (Exception e) {
                // Log error but maybe continue? Or throw?
                System.err.println("Failed to restore stock for product " + item.getProductId());
            }
        }
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }

    // DTO for order item request
    public static class OrderItemRequest {
        private Integer productId;
        private Integer quantity;

        public OrderItemRequest() {
        }

        public OrderItemRequest(Integer productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
