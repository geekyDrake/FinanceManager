package com.roshan.financemanager.service;

import com.roshan.financemanager.domain.database.OneOffExpenseEntity;
import com.roshan.financemanager.domain.database.YearlySubscriptionEntity;
import com.roshan.financemanager.domain.dto.MonthlySubscription;
import com.roshan.financemanager.domain.database.MonthlySubscriptionEntity;
import com.roshan.financemanager.domain.dto.OneOffExpense;
import com.roshan.financemanager.domain.dto.YearlySubscription;
import com.roshan.financemanager.repository.OneOffExpenseDB;
import com.roshan.financemanager.repository.MonthlySubscriptionDB;
import com.roshan.financemanager.repository.YearlySubscriptionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionDatabaseManagerService {

    @Autowired
    private MonthlySubscriptionDB monthlySubscriptionDB;
    @Autowired
    private YearlySubscriptionDB yearlySubscriptionDB;
    @Autowired
    private OneOffExpenseDB oneOffExpenseDB;

    public void saveSubscription(final MonthlySubscription monthlySubscription) {
        monthlySubscriptionDB.save(new MonthlySubscriptionEntity(monthlySubscription));
    }

    public void saveSubscription(final YearlySubscription yearlySubscription) {
        yearlySubscriptionDB.save(new YearlySubscriptionEntity(yearlySubscription));
    }

    public void saveExpense(final OneOffExpense oneOffExpense) {
        oneOffExpenseDB.save(new OneOffExpenseEntity(oneOffExpense));
    }

}
