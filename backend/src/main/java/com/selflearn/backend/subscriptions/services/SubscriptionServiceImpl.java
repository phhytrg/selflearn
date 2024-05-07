package com.selflearn.backend.subscriptions.services;

import com.selflearn.backend.subscriptions.Subscription;
import com.selflearn.backend.subscriptions.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> saveAll(List<Subscription> subscriptions) {
        return subscriptionRepository.saveAll(subscriptions);
    }
}
