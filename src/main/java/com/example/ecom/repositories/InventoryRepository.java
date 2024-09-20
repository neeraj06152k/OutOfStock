package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import com.example.ecom.models.User;

import java.util.Optional;

public interface InventoryRepository extends MyRepository<Inventory>{

    Optional<Inventory> findByProduct(Product product);
}
