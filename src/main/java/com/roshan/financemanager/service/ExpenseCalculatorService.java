package com.roshan.financemanager.service;

import com.roshan.financemanager.domain.database.MonthlySubscriptionEntity;
import com.roshan.financemanager.domain.database.OneOffExpenseEntity;
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
        runningTotal += getYearlyTotalMonthEquivalent(yearly, yearlySubscriptionEndDate, today);

        return runningTotal;
    }

    /*
    Logic is flawed:
        - if we have a monthly subscription of £12 with start_date 15th of March and end_date 15th May, by this logic:
            - March total is: £12
            - April total is: £12
            - May total (given it's before 15th) will show: £12
            - May total (given it's after 15th) will show: £0
        - if we have a yearly subscription of £120 with start_date 15th March 2020 and end_date 15th March 2021, by this logic:
            - March 2020 total is: £10
            - Every proceeding month total till end_date is £10
            - March 2021 pre 15th total is: £10
            - March 2021 post 15th total is: £0

     Need logic that handles end_date in current month:
        - Could check if end_date is in current month, if so attribute £0. This means first month (15 days in our example) would be attributed the full month's cost while the final month has 0 cost
        - Could pro-rata and accept first and last month's will share a whole month's amount

    Decision:
        - Pro-rata start and end months:
            - Most accurate
            - Avoids clunky end month handling
            - However will have jumps/dives in monthly cost
     */
    private Long getYearlyTotalMonthEquivalent(final YearlySubscriptionEntity yearly, final Optional<LocalDate> yearlySubscriptionEndDate, final LocalDate today) {
        if (yearlySubscriptionEndDate.isPresent() && yearlySubscriptionEndDate.get().isBefore(today)) {
            return 0L;
        }
        return yearlySubscriptionEndDate.isPresent() ? yearly.getAmount() / 12 : 0L;
    }

    private static Long getMonthlyTotal(final MonthlySubscriptionEntity monthly, final Optional<LocalDate> monthlySubscriptionEndDate, final LocalDate today) {
        if (monthlySubscriptionEndDate.isEmpty() || monthlySubscriptionEndDate.get().isAfter(today)) {
            return monthly.getAmount();
        }
        return 0L;
    }

    /* Should take in oneoffexpense and output the month equivalent (30 days)
        - check date is after today
        - Get daily rate (days between)
        - Multiply daily rate by however many days in current month
     */
    private Long oneOffExpenseMonthlyEquivalent(final OneOffExpenseEntity oneOffExpense) {

        return 0L;
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
    private Long getYearlyTotalProRata(final YearlySubscriptionEntity yearly, final Optional<LocalDate> yearlySubscriptionEndDate, final LocalDate today) {
        if (yearlySubscriptionEndDate.isPresent() && yearlySubscriptionEndDate.get().isBefore(today)) {
            return 0L;
        } else if (yearlySubscriptionEndDate.isPresent()) {
            return isDateInCurrentMonth(yearlySubscriptionEndDate.get(), today) ? proRataMonth(yearly.getAmount() / 12, today, MONTH_TYPE.END) : yearly.getAmount() / 12;
        }
        return yearly.getAmount() / 12;
    }

    /*

     */
    private static Long getMonthlyTotalProRata(final MonthlySubscriptionEntity monthly, final Optional<LocalDate> monthlySubscriptionEndDate, final LocalDate today) {
        if (monthlySubscriptionEndDate.isEmpty()) { return monthly.getAmount(); }
        if (monthlySubscriptionEndDate.get().isBefore(today)) { return 0L; }

        if (monthlySubscriptionEndDate.get().isAfter(today)) {
            return isDateInCurrentMonth(monthlySubscriptionEndDate.get(), today) ? proRataMonth(monthly.getAmount(), today, MONTH_TYPE.END) : monthly.getAmount();
        }
        return 0L;
    }
}
