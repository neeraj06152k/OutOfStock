package com.example.ecom.repositories;

import java.util.Optional;

public interface MyRepository<T> {
    T save(T t);
    Optional<T> findById(int id);
}
