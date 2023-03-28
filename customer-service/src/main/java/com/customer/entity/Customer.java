package com.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "name field can not be empty")
    @NotBlank(message = "name field can not be blank")
    private String name;

    @NotEmpty(message = "email field can not be empty")
    @NotBlank(message = "email field can not be blank")
    @Email(message = "please provide a valid email address")
    private String email;

    @NotEmpty(message = "phone number can not be empty")
    @NotBlank(message = "phone number can not be blank")
    @Pattern(regexp="(^$|[0-9]{10})", message = "please provide a valid phone number")
    private String phNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
