package com.example.ecom.repositories;

import com.example.ecom.models.Notification;
import com.example.ecom.models.User;

import java.util.List;

public interface NotificationRepository extends MyRepository<Notification>{
    void deleteById(int id);

    List<Notification> findByProductId(int productId);
}
