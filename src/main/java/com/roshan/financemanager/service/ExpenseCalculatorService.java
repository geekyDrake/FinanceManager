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

        runningTotal += getMonthlyTotal(monthly, monthlySubscriptionEndDate, today);
        runningTotal += getYearlyTotal(yearly, yearlySubscriptionEndDate, today);

        return runningTotal;
    }

    /* Logic is slightly broken:
    - if we have a monthly subscription of £12 with start_date 15th of March and end_date 15th May, by this logic:
        - March monthly total is £12 -> this should technically be £6
        - April monthly total is £12
        - May monthly total is £6
     - if we have a yearly subscription of £120 with start_date 15th March 2020 and end_date 15th March 2021, by this logic:
        - March 2020 total is £10 -> this should technically be £5
        - Every month before March 2021 is £10
        - March 2021 total is £5
     */
    private Long getYearlyTotal(final YearlySubscriptionEntity yearly, final Optional<LocalDate> yearlySubscriptionEndDate, final LocalDate today) {
        if (yearlySubscriptionEndDate.isPresent() && yearlySubscriptionEndDate.get().isBefore(today)) {
            return 0L;
        } else if (yearlySubscriptionEndDate.isPresent()) {
            return isDateInCurrentMonth(yearlySubscriptionEndDate.get(), today) ? proRataMonth(yearly.getAmount() / 12, today) : yearly.getAmount() / 12;
        }
        return yearly.getAmount() / 12;
    }

    private static Long getMonthlyTotal(final MonthlySubscriptionEntity monthly, final Optional<LocalDate> monthlySubscriptionEndDate, final LocalDate today) {
        if (monthlySubscriptionEndDate.isEmpty() || monthlySubscriptionEndDate.get().isAfter(today)) {
            return isDateInCurrentMonth(monthlySubscriptionEndDate.get(), today) ? proRataMonth(monthly.getAmount(), today) : monthly.getAmount();
        }
        return 0L;
    }


}
