package com.roshan.financemanager.service;

import com.roshan.financemanager.domain.database.MonthlySubscriptionEntity;
import com.roshan.financemanager.domain.database.YearlySubscriptionEntity;
import com.roshan.financemanager.util.DateHelpers;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static com.roshan.financemanager.util.DateHelpers.*;
import static java.util.Optional.ofNullable;

public class ExpenseCalculatorService {

    @Autowired
    private SubscriptionDatabaseManagerService dbService;

    public Long calculateEquivalentMonthlyExpense() {
        Long runningTotal = 0L;
        // Placeholder null values - populate with real information. Maybe abstract the DB objects to domain objects if bothered
        runningTotal += subscriptionAmount(null, null);
        return runningTotal;
    }

    private Long subscriptionAmount(final MonthlySubscriptionEntity monthly, final YearlySubscriptionEntity yearly) {
        Long runningTotal = 0L;
        final var monthlySubscriptionEndDate = ofNullable(monthly.getEndDate()).map(DateHelpers::convertDateToLocalDate);
        final var yearlySubscriptionEndDate = ofNullable(yearly.getEndDate()).map(DateHelpers::convertDateToLocalDate);
        final var today = LocalDate.now();

        runningTotal = getMonthlyTotal(monthly, monthlySubscriptionEndDate, today);

        return runningTotal;
    }

    private static Long getMonthlyTotal(MonthlySubscriptionEntity monthly, Optional<LocalDate> monthlySubscriptionEndDate, LocalDate today) {
        if (monthlySubscriptionEndDate.isEmpty() || monthlySubscriptionEndDate.get().isAfter(today)) {
            if (isDateInCurrentMonth(monthlySubscriptionEndDate.get(), today)) {
                return proRataMonth(monthly.getAmount(), today);
            } else {
                return monthly.getAmount();
            }
        }
        return 0L;
    }


}
