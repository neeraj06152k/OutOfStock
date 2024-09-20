package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.Inventory;

public interface InventoryService {

    Inventory updateInventory(int productId, int quantity) throws ProductNotFoundException;
    void sendNotification(int productId);
}
