package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.util.DateHelpers;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@AllArgsConstructor
@MappedSuperclass // parent fields are persisted in child tables
public abstract class TimeEntity {
    // If no endDate, assume subscription is perpetual
    private final Date endDate;
    // If no startDate, assume subscription already started
    private final Date startDate;

    public Optional<LocalDate> parseEndDate() {
        return ofNullable(this.endDate).map(DateHelpers::convertDateToLocalDate);
    }

    public Optional<LocalDate> parseStartDate() {
        return ofNullable(this.startDate).map(DateHelpers::convertDateToLocalDate);
    }
}
