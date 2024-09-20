package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Notification;
import com.example.ecom.models.Product;
import com.example.ecom.models.User;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Inventory inventory = inventoryRepository.findByProduct(product)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if(inventory.getQuantity()>0) throw new ProductInStockException("Product is in stock");

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setProduct(product);
        return notificationRepository.save(notification);
    }

    @Override
    public void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
        if(notification.getUser().getId()!=user.getId()) throw new UnAuthorizedException("User not authorized to delete this notification");

        notificationRepository.deleteById(notificationId);
    }

    @Override
    public List<User> getSubscribers(int productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        List<Notification> notifications = notificationRepository.findByProductId(productId);

        return List.of();
    }
}
