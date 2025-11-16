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
        final var today = LocalDate.now();

        runningTotal += getMonthlyTotalProRata(monthly, today);
        runningTotal += getYearlyEquivalentMonthTotalProRata(yearly, today);

        return runningTotal;
    }

    /*
     Need logic that handles end_date in current month:
        - Could check if end_date is in current month, if so attribute £0. This means first month (15 days in our example) would be attributed the full month's cost while the final month has 0 cost
        - Could pro-rata and accept first and last month's will share a whole month's amount

    Decision:
        - Pro-rata start and end months:
            - Most accurate
            - Avoids clunky end month handling
            - However will have jumps/dives in monthly cost
     */

    /*
    Yearly logic:
    - If start date is present and in current month: return pro-rata amount (yearly/365 * #days where #days = start_date to end of month)
    - If end date is not present return monthly amount (yearly/365 * #days_in_month)
    - If end date is present and in current month: return pro-rata amount (yearly/365 * #days where #days = start of month to end_date)
    - If end date is present and is before beginning of the month, return 0 else return monthly amount (yearly/365 * #days_in_month)
     */
    private Long getYearlyEquivalentMonthTotalProRata(final YearlySubscriptionEntity yearly, final LocalDate today) {
        if (yearly.parseStartDate().isPresent() && isDateInCurrentMonth(yearly.parseStartDate().get(), today)) {
            return proRataYearsToMonth(yearly.getAmount(), yearly.parseStartDate().get(), MONTH_TYPE.START);
        }
        // Flawed as number of days in year can vary but acceptable for now
        final var monthlyEquivalentAmount = (yearly.getAmount() / today.lengthOfYear()) * today.lengthOfMonth();

        if (yearly.parseEndDate().isEmpty()) {
            return monthlyEquivalentAmount;
        }

        if (yearly.parseEndDate().isPresent() && isDateInCurrentMonth(yearly.parseEndDate().get(), today)) {
            return proRataYearsToMonth(yearly.getAmount(), yearly.parseEndDate().get(), MONTH_TYPE.END);
        }

        if (yearly.parseEndDate().get().isBefore(today)) {
            return 0L;
        }

        return monthlyEquivalentAmount;
    }

    /*
    Monthly logic:
    - If start date is present and in current month: return pro-rata amount
    - If end date is not present return monthly amount
    - If end date is present and in current month: return pro-rata amount
    - If end date is present and is before beginning of the month, return 0 else return monthly amount
     */
    private static Long getMonthlyTotalProRata(final MonthlySubscriptionEntity monthly, final LocalDate today) {
        if (monthly.parseStartDate().isPresent() && isDateInCurrentMonth(monthly.parseStartDate().get(), today)) {
            return proRataMonth(monthly.getAmount(), monthly.parseStartDate().get(), MONTH_TYPE.START);
        }

        if (monthly.parseEndDate().isEmpty()) {
            return monthly.getAmount();
        }

        if (monthly.parseEndDate().isPresent() && isDateInCurrentMonth(monthly.parseEndDate().get(), today)) {
            return proRataMonth(monthly.getAmount(), monthly.parseEndDate().get(), MONTH_TYPE.END);
        }

        if (monthly.parseEndDate().get().isBefore(today)) {
            return 0L;
        }

        return monthly.getAmount();
    }

    /* Should take in oneoffexpense and output the month equivalent (30 days)
        - check date is after today
        - Get daily rate (days between)
        - Multiply daily rate by however many days in current month
     */
    private Long oneOffExpenseMonthlyEquivalent(final OneOffExpenseEntity oneOffExpense) {

        return 0L;
    }


}
