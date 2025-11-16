package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.domain.dto.MonthlySubscription;
import com.roshan.financemanager.domain.dto.YearlySubscription;
import com.roshan.financemanager.util.DateHelpers;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Getter
@Entity
public class YearlySubscriptionEntity extends TimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private final Long subscriptionEntityId;

  public YearlySubscriptionEntity(final String name, final Long amount, final Date endDate,
      final Date startDate) {
    super(name, amount, endDate, startDate);
    this.subscriptionEntityId = null;
  }

  public YearlySubscriptionEntity(final YearlySubscription s) {
    this(s.getName(), s.getAmount(), s.getEndDate(), s.getStartDate());
  }
}
