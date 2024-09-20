package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.libraries.SendNotification;
import com.example.ecom.libraries.SendNotificationImpl;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Notification;
import com.example.ecom.models.Product;
import com.example.ecom.models.User;
import com.example.ecom.repositories.InMemNotificationRepository;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService{

    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;
    private NotificationRepository notificationRepository = new InMemNotificationRepository();
    private SendNotification sendNotification = new SendNotificationImpl();

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Inventory updateInventory(int productId, int quantity) throws ProductNotFoundException {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Optional<Inventory> inventoryOptional = this.inventoryRepository.findByProduct(product);
        Inventory inventory;
        if(inventoryOptional.isEmpty()){
            inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(quantity);
            return this.inventoryRepository.save(inventory);
        }
        inventory = inventoryOptional.get();
        inventory.setQuantity(inventory.getQuantity() + quantity);
        return this.inventoryRepository.save(inventory);
    }

    @Override
    public void sendNotification(int productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        String subject = "{productname} back in stock!".formatted(product.getName());
        // send notification
        List<Notification> notifications = notificationRepository.findByProductId(productId);
        for (Notification notification : notifications) {
            User user = notification.getUser();
            String emailBody = "Dear {username}, {product_name} is now back in stock. Grab it ASAP!"
                    .formatted(user.getName(), product.getName());

            sendNotification.notify(user.getEmail(), subject, emailBody);
        }
    }
}
