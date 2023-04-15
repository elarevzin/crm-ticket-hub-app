package com.erevzin.crmtickethub.datamodel;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "CRM_TICKET")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CrmTicket {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ERROR_CODE")
    private Integer errorCode;

    @Column(name = "PROVIDER_NAME")
    private String providerName;
    @Column(name = "ORIGINAL_CASE_ID")
    private Integer originalCaseId;
    @Column(name = "CUSTOMER_ID")
    private Integer customerId;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;
    @Column(name = "TICKET_STATUS")
    private CrmTicketStatus status;
    @Column(name = "PRODUCT_NAME")
    private String productName;
    private CrmSystemName originalSystemName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmTicket that = (CrmTicket) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(originalCaseId, that.originalCaseId) &&
                Objects.equals(originalSystemName, that.originalSystemName) &&
                Objects.equals(errorCode, that.errorCode) &&
                Objects.equals(providerName, that.providerName) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(customerId, that.customerId) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalCaseId, originalSystemName, customerId, providerName, errorCode, status, productName );
    }


}
