package com.example.department.shared;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessProfile {
    private Long id;
    private String companyName;
    private String legalName;
    private Address address;
    private TaxIdentifier taxIdentifier;
    private String email;
    private String website;
}
