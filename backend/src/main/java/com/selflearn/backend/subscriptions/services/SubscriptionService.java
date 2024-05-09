package com.selflearn.backend.subscriptions.services;

import com.selflearn.backend.subscriptions.Subscription;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> saveAll(List<Subscription> subscriptions);

    List<Subscription> fetchAll();
    void deleteAll();
}
