package com.example.department.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxIdentifier {

    private TaxType taxType;
    private String serialNumber;

    enum TaxType {
        PAN, EIN;
    }
}
