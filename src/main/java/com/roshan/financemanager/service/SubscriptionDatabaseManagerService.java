package com.roshan.financemanager.service;

import com.roshan.financemanager.domain.dto.MonthlySubscription;
import com.roshan.financemanager.domain.database.MonthlySubscriptionEntity;
import com.roshan.financemanager.repository.SubscriptionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionDatabaseManagerService {

    @Autowired
    private SubscriptionDB subscriptionDB;

    public void saveSubscription(final MonthlySubscription monthlySubscription) {
        subscriptionDB.save(new MonthlySubscriptionEntity(monthlySubscription));
    }
}
