package com.roshan.financemanager.domain.database;

import static com.roshan.financemanager.util.DateHelpers.convertDateToLocalDate;

import com.roshan.financemanager.domain.dto.OneOffExpense;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class OneOffExpenseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long oneOffExpenseEntityId;
  private String name;
  @Getter(AccessLevel.NONE) // to provide custom getter
  private Date startDate;
  @Getter(AccessLevel.NONE) // to provide custom getter
  private Date endDate;
  private Long amount;

  public OneOffExpenseEntity(final OneOffExpense o) {
    this(null, o.getName(), o.getStartDate(), o.getRealEndDate(), o.getAmount());
  }

  public LocalDate getStartDate(){
    return convertDateToLocalDate(this.startDate);
  }

  public LocalDate getEndDate(){
    return convertDateToLocalDate(this.endDate);
  }
}
