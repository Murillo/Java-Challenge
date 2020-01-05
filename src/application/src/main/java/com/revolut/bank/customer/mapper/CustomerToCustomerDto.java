package com.revolut.bank.customer.mapper;

import com.revolut.bank.customer.business.Customer;
import com.revolut.bank.customer.dto.CustomerDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomerToCustomerDto {

    private CustomerToCustomerDto() {}

    private static final String FULL_NAME_PATTER = "%s %s %s";
    private static final String FULL_NAME_PATTER_WITHOUT_MIDDLE_NAME = "%s %s";

    public static CustomerDto map(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCode(customer.getCode());
        if (Objects.isNull(customer.getMiddleName())) {
            customerDto.setFullName(String.format(FULL_NAME_PATTER_WITHOUT_MIDDLE_NAME,
                    customer.getName(),
                    customer.getLastName()));
        } else {
            customerDto.setFullName(String.format(FULL_NAME_PATTER,
                    customer.getName(),
                    customer.getMiddleName(),
                    customer.getLastName()));
        }
        customerDto.setEmail(customer.getContact().getEmail());
        customerDto.setPhone(customer.getContact().getPhone());
        return customerDto;
    }

    public static List<CustomerDto> mapCollection(List<Customer> customerList){
        return customerList.stream()
                .map(CustomerToCustomerDto::map)
                .collect(Collectors.toList());
    }

}
