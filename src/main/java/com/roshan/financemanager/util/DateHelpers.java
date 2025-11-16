package com.roshan.financemanager.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateHelpers {

  public enum MONTH_TYPE {
    START,
    END
  }

  public static LocalDate convertDateToLocalDate(final Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static boolean isDateInCurrentMonth(final LocalDate givenDate, final LocalDate today) {
    return givenDate.getYear() == today.getYear() && givenDate.getMonth() == today.getMonth();
  }

  /*
  If subscription starts in current month, we want to pro-rata by start_date till end of the month
  If subscription ends in current month, we want to pro-rata by start of the month, till end date
  Still slightly flawed as we divide by however many days there are in the month which is variable...will accept for now
   */
  public static Long proRataMonth(final Long monthlyAmount, final LocalDate givenDay,
      MONTH_TYPE monthType) {
    final long daysIncluded = getNumberOfDaysToInclude(givenDay, monthType);

    return multiplyUpDayRate(monthlyAmount, (long) givenDay.lengthOfMonth(), daysIncluded);
  }

  /*
  If subscription starts in current month, we want to pro-rata by start_date till end of the month
  If subscription ends in current month, we want to pro-rata by start of the month, till end date
  Still slightly flawed as we divide by however many days there are in the year which is variable...will accept for now
   */
  public static Long proRataYearsToMonth(final Long yearlyAmount, final LocalDate givenDay,
      MONTH_TYPE monthType) {
    final long daysIncluded = getNumberOfDaysToInclude(givenDay, monthType);

    return multiplyUpDayRate(yearlyAmount, (long) givenDay.lengthOfYear(), daysIncluded);
  }

  private static long getNumberOfDaysToInclude(LocalDate givenDay, MONTH_TYPE monthType) {
    final long daysBetweenStartAndEnd = monthType.equals(MONTH_TYPE.START)
        ? ChronoUnit.DAYS.between(givenDay, givenDay.withDayOfMonth(givenDay.lengthOfMonth())) :
        // exclusive of final day
        ChronoUnit.DAYS.between(givenDay.withDayOfMonth(1), givenDay) + 1; // inclusive of final day
    return daysBetweenStartAndEnd;
  }

  private static Long multiplyUpDayRate(final Long amount, final Long daysDenominator,
      final Long daysAttributable) {
    return (amount / daysDenominator) * daysAttributable;
  }

}
