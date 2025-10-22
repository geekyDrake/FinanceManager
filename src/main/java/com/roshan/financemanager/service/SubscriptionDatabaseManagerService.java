package com.roshan.financemanager.service;

import com.roshan.financemanager.domain.Subscription;
import com.roshan.financemanager.domain.database.SubscriptionEntity;
import com.roshan.financemanager.repository.SubscriptionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionDatabaseManagerService {

    @Autowired
    private SubscriptionDB subscriptionDB;

    public void saveSubscription(final Subscription subscription) {
        subscriptionDB.save(new SubscriptionEntity(subscription));
    }
}
