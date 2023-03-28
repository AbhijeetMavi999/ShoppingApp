package com.customer.payloads;

import com.customer.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerResponse {

    private List<Customer> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalRecords;
    private Integer totalPages;

    private Boolean isLastPage;
}
