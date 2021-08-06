package com.intuit.app.entity.business_profile;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessProfile {
    private String companyName;
    private String legalName;
    private Address address;
    private TaxIdentifier taxIdentifier;
    private String email;
    private String website;

    public static class BusinessProfileBuilder {
        private String companyName;
        private String legalName;
        private Address address;
        private TaxIdentifier taxIdentifier;
        private String email;
        private String website;

        public BusinessProfileBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public BusinessProfileBuilder legalName(String legalName) {
            this.legalName = legalName;
            return this;
        }

        public BusinessProfileBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public BusinessProfileBuilder taxIdentifier(TaxIdentifier taxIdentifier) {
            this.taxIdentifier = taxIdentifier;
            return this;
        }

        public BusinessProfileBuilder email(String email) {
            this.email = email;
            return this;
        }

        public BusinessProfileBuilder website(String website) {
            this.website = website;
            return this;
        }

        public BusinessProfile build() {
            return new BusinessProfile(companyName, legalName, address, taxIdentifier, email, website);
        }
    }
}
