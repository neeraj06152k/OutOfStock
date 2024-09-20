package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemInventoryRepository implements InventoryRepository {
    private final Map<Integer, Inventory> map = new HashMap<>();

    @Override
    public Optional<Inventory> findByProduct(Product product) {
        for(Inventory inventory: map.values()){
            if(inventory.getProduct().equals(product)){
                return Optional.of(inventory);
            }
        }
        return Optional.empty();
    }

    @Override
    public Inventory save(Inventory inventory) {

        if(inventory.getId()==0) {
            inventory.setId(map.keySet().stream().max(Integer::compare).orElse(0)+1);
        }
        map.put(inventory.getId(),inventory);
        return map.get(inventory.getId());
    }

    @Override
    public Optional<Inventory> findById(int id) {
        return Optional.ofNullable(map.get(id));
    }
}
