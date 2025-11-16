package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.util.DateHelpers;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@AllArgsConstructor
@MappedSuperclass // parent fields are persisted in child tables
public abstract class TimeEntity {
    private final String name;
    /*
    - Yearly: Assume amount is for an arbitary year if no endDate is given (i.e. divide by 12)
    - Monthly: Assume amount is for an arbitary month if no endDate is given
     */
    private final Long amount;
    // If no endDate, assume subscription is perpetual
    private final Date endDate;
    // If no startDate, assume subscription already started a long time ago
    private final Date startDate;

    public Optional<LocalDate> parseEndDate() {
        return ofNullable(this.endDate).map(DateHelpers::convertDateToLocalDate);
    }

    public Optional<LocalDate> parseStartDate() {
        return ofNullable(this.startDate).map(DateHelpers::convertDateToLocalDate);
    }
}
