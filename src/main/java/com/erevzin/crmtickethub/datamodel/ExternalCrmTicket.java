package com.erevzin.crmtickethub.datamodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalCrmTicket {
    @JsonProperty("case_id")
    private Integer caseId;
    @JsonProperty("customer_id")
    private Integer customerId;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("created_error_code")
    private Integer createdErrorCode;
    @JsonProperty("status")
    private String status;
    @JsonProperty("ticket_creation_date")
    private String ticketCreationDate;
    @JsonProperty("last_modified_date")
    private String lastModifiedDate;
    @JsonProperty("product_name")
    private String productName;
    private CrmSystemName originalSystemName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExternalCrmTicket that = (ExternalCrmTicket) o;
        return Objects.equals(caseId, that.caseId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(originalSystemName, that.originalSystemName) &&
                Objects.equals(provider, that.provider) &&
                Objects.equals(createdErrorCode, that.createdErrorCode) &&
                Objects.equals(status, that.status) &&
                Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseId, customerId, originalSystemName, provider, createdErrorCode, status, productName );
    }
}
