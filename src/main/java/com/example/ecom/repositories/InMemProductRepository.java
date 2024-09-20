package com.example.ecom.repositories;

import com.example.ecom.models.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemProductRepository implements ProductRepository {
    private final Map<Integer, Product> map = new HashMap<>();

    @Override
    public Product save(Product product) {
        if (product.getId() == 0) {
            product.setId(map.keySet().stream().max(Integer::compare).orElse(0) + 1);
        }
        map.put(product.getId(), product);
        return map.get(product.getId());
    }

    @Override
    public Optional<Product> findById(int id) {
        return Optional.ofNullable(map.get(id));
    }
}
