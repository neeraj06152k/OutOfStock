package com.example.ecom.repositories;

import com.example.ecom.models.Notification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemNotificationRepository implements NotificationRepository {
    private final Map<Integer,Notification> map = new HashMap<>();
    @Override
    public Notification save(Notification notification) {
        if(notification.getId()==0) {
            notification.setId(map.keySet().stream().max(Integer::compare).orElse(0)+1);
        }
        map.put(notification.getId(),notification);
        return map.get(notification.getId());
    }

    @Override
    public Optional<Notification> findById(int id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void deleteById(int id) {
        map.remove(id);
    }

    @Override
    public List<Notification> findByProductId(int productId) {
        return map.values().stream()
                .filter(notification -> notification.getProduct().getId()==productId)
                .toList();
    }
}
