package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.Notification;
import com.example.ecom.models.User;

import java.util.List;

public interface NotificationService {

    Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException;

    void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException;

    List<User> getSubscribers(int productId) throws ProductNotFoundException;
}
