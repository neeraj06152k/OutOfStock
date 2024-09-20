package com.example.ecom.repositories;

import com.example.ecom.models.User;

import java.util.*;
import java.util.Optional;

public class InMemUserRepository implements UserRepository{
    private final Map<Integer,User> map = new HashMap<>();
    @Override
    public User save(User user) {
        if(user.getId()==0) {
            user.setId(map.keySet().stream().max(Integer::compare).orElse(0)+1);
        }
        map.put(user.getId(),user);
        return map.get(user.getId());
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(map.get(id));
    }
}
