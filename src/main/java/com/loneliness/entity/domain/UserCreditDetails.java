package com.loneliness.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true, of = {"creditCardNumber"})
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue(value = "UserCreditDetails")
public class UserCreditDetails extends User {

    private String creditCardNumber;
    private BigDecimal sumOfMoney;
}
