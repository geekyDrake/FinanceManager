package com.roshan.financemanager.domain.database;

import com.roshan.financemanager.domain.dto.OneOffExpense;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private Date startDate;
  private Date endDate;
  private Long amount;

  public OneOffExpenseEntity(final OneOffExpense o) {
    this(null, o.getName(), o.getStartDate(), o.getRealEndDate(), o.getAmount());
  }
}
