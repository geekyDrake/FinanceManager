package com.roshan.financemanager.service;

import com.roshan.financemanager.domain.database.MonthlySubscriptionEntity;
import com.roshan.financemanager.domain.database.OneOffExpenseEntity;
import com.roshan.financemanager.domain.database.YearlySubscriptionEntity;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.roshan.financemanager.util.DateHelpers.*;

public class ExpenseCalculatorService {

  @Autowired
  private SubscriptionDatabaseManagerService dbService;

  // Ideally I wouldn't work with the DB objects directly, but it's not worth going back and changing to some domain level object now
  public Long calculateEquivalentMonthlyExpense(
      List<MonthlySubscriptionEntity> monthlySubscriptions,
      List<YearlySubscriptionEntity> yearlySubscriptions,
      List<OneOffExpenseEntity> oneOffExpenses) {
    AtomicReference<Long> runningTotal = new AtomicReference<>(0L);
    final var today = LocalDate.now();

    monthlySubscriptions.forEach(sub -> runningTotal.updateAndGet(
        rt -> rt + getMonthlyTotalProRata(sub, today)));

    yearlySubscriptions.forEach(sub -> runningTotal.updateAndGet(
        rt -> rt + getYearlyEquivalentMonthTotalProRata(sub, today)));

    oneOffExpenses.forEach(expense -> runningTotal.updateAndGet(
        rt -> rt + oneOffExpenseMonthlyEquivalent(expense, today)));

    return runningTotal.get();
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
  private Long getYearlyEquivalentMonthTotalProRata(final YearlySubscriptionEntity yearly,
      final LocalDate today) {
    if (yearly.parseStartDate().isPresent() && isDateInCurrentMonth(yearly.parseStartDate().get(),
        today)) {
      return proRataYearsToMonth(yearly.getAmount(), yearly.parseStartDate().get(),
          MONTH_TYPE.START);
    }
    // Flawed as number of days in year can vary but acceptable for now
    final var monthlyEquivalentAmount =
        (yearly.getAmount() / today.lengthOfYear()) * today.lengthOfMonth();

    if (yearly.parseEndDate().isEmpty()) {
      return monthlyEquivalentAmount;
    }

    if (yearly.parseEndDate().isPresent() && isDateInCurrentMonth(yearly.parseEndDate().get(),
        today)) {
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
  private static Long getMonthlyTotalProRata(final MonthlySubscriptionEntity monthly,
      final LocalDate today) {
    if (monthly.parseStartDate().isPresent() && isDateInCurrentMonth(monthly.parseStartDate().get(),
        today)) {
      return proRataMonth(monthly.getAmount(), monthly.parseStartDate().get(), MONTH_TYPE.START);
    }

    if (monthly.parseEndDate().isEmpty()) {
      return monthly.getAmount();
    }

    if (monthly.parseEndDate().isPresent() && isDateInCurrentMonth(monthly.parseEndDate().get(),
        today)) {
      return proRataMonth(monthly.getAmount(), monthly.parseEndDate().get(), MONTH_TYPE.END);
    }

    if (monthly.parseEndDate().get().isBefore(today)) {
      return 0L;
    }

    return monthly.getAmount();
  }

  /* Should take in oneoffexpense and output the month equivalent (30 days)
  OneOff logic:
  - If start date is in current month: return pro-rata amount
  - If end date is in current month: return pro-rata amount
  - If end date is before beginning of the month, return 0 else return monthly amount
      - check date is after today
      - Get daily rate (days between)
      - Multiply daily rate by however many days in current month
   */
  private Long oneOffExpenseMonthlyEquivalent(final OneOffExpenseEntity oneOffExpense,
      final LocalDate today) {
    final var expensePeriodDays = ChronoUnit.DAYS.between(oneOffExpense.getStartDate(),
        oneOffExpense.getEndDate());
    if (isDateInCurrentMonth(oneOffExpense.getStartDate(), today)) {
      return multiplyUpDayRate(oneOffExpense.getAmount(),
          expensePeriodDays,
          ChronoUnit.DAYS.between(oneOffExpense.getStartDate(), today.withDayOfMonth(
              today.lengthOfMonth())));
    }

    if (isDateInCurrentMonth(oneOffExpense.getEndDate(), today)) {
      return multiplyUpDayRate(oneOffExpense.getAmount(),
          expensePeriodDays,
          ChronoUnit.DAYS.between(today.withDayOfMonth(1), oneOffExpense.getEndDate()));
    }

    if (oneOffExpense.getEndDate().isBefore(today)) {
      return 0L;
    }

    return multiplyUpDayRate(oneOffExpense.getAmount(), (long) today.lengthOfYear(),
        (long) today.lengthOfMonth());
  }
}
