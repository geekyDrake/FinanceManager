package com.roshan.financemanager.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateHelpers {

    public static LocalDate convertDateToLocalDate(final Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static boolean isDateInCurrentMonth(final LocalDate givenDate, final LocalDate today){
        return givenDate.getYear() == today.getYear() && givenDate.getMonth() == today.getMonth();
    }

    public static Long proRataMonth(final Long amount, final LocalDate givenDay){
        return proRataByNumberOfDays(amount, (long) givenDay.lengthOfMonth(), ChronoUnit.DAYS.between(givenDay.withDayOfMonth(1), givenDay) + 1);
    }

    public static Long proRataAmount(final Long amount, final LocalDate startDate, final LocalDate endDate, final Long days){
        final var daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return proRataByNumberOfDays(amount, daysBetween, days);
    }

    public static Long proRataByNumberOfDays(final Long amount, final Long daysInBetweenStarAndEnd, final Long daysTillGivenDate){
        return (amount / daysInBetweenStarAndEnd) * daysTillGivenDate;
    }

}
