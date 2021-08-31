package com.myntra.hackathon.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String flatNo;
    private String apartment;
    private String street;
    private String district;
    private String state;
    private Integer pin;
    private boolean primary;
}
