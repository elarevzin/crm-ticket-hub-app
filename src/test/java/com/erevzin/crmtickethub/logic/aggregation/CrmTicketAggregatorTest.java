package com.erevzin.crmtickethub.logic.aggregation;

import com.erevzin.crmtickethub.datamodel.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrmTicketAggregatorTest {


    public static final Date LAST_UPDATE_DATE = new Date();
    public static final String PROVIDER_1 = "Provider1";
    public static final String RED = "RED";
    public static final String GREEN = "GREEN";
    public static final String PROVIDER_2 = "Provider2";
    private final CrmTicketAggregator crmTicketAggregator = new CrmTicketAggregator();

    @Test
    public void testApply() {
        // Create some test data
        CrmTicket ticket1 = CrmTicket.builder()
                .errorCode(100)
                .providerName(PROVIDER_1)
                .originalCaseId(1)
                .originalSystemName(CrmSystemName.BANANA)
                .productName(RED)
                .customerId(1)
                .status(CrmTicketStatus.OPEN)
                .lastModifiedDate(LAST_UPDATE_DATE)
                .build();
        CrmTicket ticket2 = CrmTicket.builder()
                .errorCode(200)
                .providerName(PROVIDER_1)
                .originalCaseId(2)
                .originalSystemName(CrmSystemName.BANANA)
                .productName(GREEN)
                .customerId(2)
                .status(CrmTicketStatus.IN_PROGRESS)
                .lastModifiedDate(LAST_UPDATE_DATE)
                .build();
        CrmTicket ticket3 = CrmTicket.builder()
                .errorCode(100)
                .providerName(PROVIDER_2)
                .originalCaseId(3)
                .originalSystemName(CrmSystemName.BANANA)
                .productName(RED)
                .customerId(3)
                .status(CrmTicketStatus.CLOSED)
                .lastModifiedDate(LAST_UPDATE_DATE)
                .build();
        CrmTicket ticket4 = CrmTicket.builder()
                .errorCode(300)
                .providerName("Provider4")
                .originalCaseId(4)
                .originalSystemName(CrmSystemName.STRAWBERRY)
                .productName(GREEN)
                .customerId(4)
                .status(CrmTicketStatus.OPEN)
                .lastModifiedDate(LAST_UPDATE_DATE)
                .build();
        CrmTicket ticket5 = CrmTicket.builder()
                .errorCode(100)
                .providerName(PROVIDER_1)
                .originalCaseId(5)
                .originalSystemName(CrmSystemName.STRAWBERRY)
                .productName(GREEN)
                .customerId(5)
                .status(CrmTicketStatus.IN_PROGRESS)
                .lastModifiedDate(LAST_UPDATE_DATE)
                .build();

        List<CrmTicket> crmTickets = Arrays.asList(ticket1, ticket2, ticket3, ticket4, ticket5);

        // Create the expected results
        List<CrmTicketAggregate> expectedAggregates = new ArrayList<>();
        expectedAggregates.add(CrmTicketAggregate.builder()
                .errorCode(100)
                .providerName(PROVIDER_1)
                .relatedCaseIds(Arrays.asList("1-BANANA", "5-STRAWBERRY"))
                .affectedProducts(Arrays.asList(GREEN, RED))
                .status(CrmTicketStatus.OPEN)
                .affectedCustomers(Arrays.asList(1, 5))
                .lastUpdateDate(LAST_UPDATE_DATE)
                .build());
        expectedAggregates.add(CrmTicketAggregate.builder()
                .errorCode(100)
                .providerName(PROVIDER_2)
                .relatedCaseIds(Arrays.asList("3-BANANA"))
                .affectedProducts(Arrays.asList(RED))
                .status(CrmTicketStatus.CLOSED)
                .affectedCustomers(Arrays.asList(3))
                .lastUpdateDate(LAST_UPDATE_DATE)
                .build());
        expectedAggregates.add(CrmTicketAggregate.builder()
                .errorCode(200)
                .providerName(PROVIDER_1)
                .relatedCaseIds(Arrays.asList("2-BANANA"))
                .affectedProducts(Arrays.asList(GREEN))
                .status(CrmTicketStatus.IN_PROGRESS)
                .affectedCustomers(Arrays.asList(2))
                .lastUpdateDate(LAST_UPDATE_DATE)
                .build());
        expectedAggregates.add(CrmTicketAggregate.builder()
                .errorCode(300)
                .providerName("Provider4")
                .relatedCaseIds(Arrays.asList("4-STRAWBERRY"))
                .affectedProducts(Arrays.asList(GREEN))
                .status(CrmTicketStatus.OPEN)
                .affectedCustomers(Arrays.asList(4))
                .lastUpdateDate(LAST_UPDATE_DATE)
                .build());

        List<CrmTicketAggregate> actualAggregates = crmTicketAggregator.apply(crmTickets);
        assertTrue(actualAggregates.containsAll(expectedAggregates));

    }
}