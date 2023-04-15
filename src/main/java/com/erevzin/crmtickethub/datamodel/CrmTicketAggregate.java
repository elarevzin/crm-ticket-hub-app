package com.erevzin.crmtickethub.datamodel;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "AGGREGATED_TICKETS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CrmTicketAggregate {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ERROR_CODE")
    private Integer errorCode;

    @Column(name = "PROVIDER_NAME")
    private String providerName;

    @ElementCollection
    @CollectionTable(name = "RELATED_CASES")
    @Column(name = "CASE_ID")
    private List<String> relatedCaseIds;

    @ElementCollection
    @CollectionTable(name = "AFFECTED_PRODUCTS")
    @Column(name = "PRODUCT_NAME")
    private List<String> affectedProducts;

    @ElementCollection
    @CollectionTable(name = "AFFECTED_CUSTOMERS")
    @Column(name = "CUSTOMER_ID")
    private List<Integer> affectedCustomers;


    @Column(name = "STATUS")
    private CrmTicketStatus status;

    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrmTicketAggregate that = (CrmTicketAggregate) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(errorCode, that.errorCode) &&
                Objects.equals(providerName, that.providerName) &&
                compareLists(relatedCaseIds, that.relatedCaseIds) &&
                compareLists(affectedProducts, that.affectedProducts) &&
                compareLists(affectedCustomers, that.affectedCustomers) &&
                status == that.status;
    }

    private boolean compareLists(List<?> list1, List<?> list2) {
        if (list1 == null || list2 == null) {
            return list1 == list2;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        List<?> sortedList1 = new ArrayList<>(list1);
        List<?> sortedList2 = new ArrayList<>(list2);
        sortedList1.sort(Comparator.comparing(Object::toString));
        sortedList2.sort(Comparator.comparing(Object::toString));
        return sortedList1.equals(sortedList2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, errorCode, providerName, relatedCaseIds, affectedProducts, affectedCustomers, status );
    }

}