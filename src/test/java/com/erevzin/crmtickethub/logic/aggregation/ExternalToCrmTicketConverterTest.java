package com.erevzin.crmtickethub.logic.aggregation;

import com.erevzin.crmtickethub.datamodel.CrmSystemName;
import com.erevzin.crmtickethub.datamodel.CrmTicket;
import com.erevzin.crmtickethub.datamodel.CrmTicketStatus;
import com.erevzin.crmtickethub.datamodel.ExternalCrmTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExternalToCrmTicketConverterTest {

    private ExternalToCrmTicketConverter converter;

    @BeforeEach
    void setUp() {
        converter = new ExternalToCrmTicketConverter();
    }

    @Test
    void apply_ShouldConvertExternalCrmTicketToCrmTicket() throws ParseException {
        ExternalCrmTicket externalCrmTicket = new ExternalCrmTicket();
        externalCrmTicket.setCaseId(1);
        externalCrmTicket.setOriginalSystemName(CrmSystemName.BANANA);
        externalCrmTicket.setCustomerId(2);
        externalCrmTicket.setProvider("Test Provider");
        externalCrmTicket.setCreatedErrorCode(123);
        externalCrmTicket.setStatus("Closed");
        externalCrmTicket.setTicketCreationDate("04/12/2023 10:00");
        externalCrmTicket.setLastModifiedDate("04/12/2023 10:30");
        externalCrmTicket.setProductName("Test Product");

        CrmTicket expectedCrmTicket = CrmTicket.builder()
                .originalCaseId(externalCrmTicket.getCaseId())
                .originalSystemName(externalCrmTicket.getOriginalSystemName())
                .customerId(externalCrmTicket.getCustomerId())
                .providerName(externalCrmTicket.getProvider())
                .errorCode(externalCrmTicket.getCreatedErrorCode())
                .status(CrmTicketStatus.CLOSED)
                .creationDate(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(externalCrmTicket.getTicketCreationDate()))
                .lastModifiedDate(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(externalCrmTicket.getLastModifiedDate()))
                .productName(externalCrmTicket.getProductName())
                .build();

        CrmTicket actualCrmTicket = converter.apply(externalCrmTicket);

        assertEquals(expectedCrmTicket, actualCrmTicket);
    }

    @Test
    void apply_ShouldSetNotDefinedStatusWhenExternalCrmTicketStatusIsNull() throws ParseException {
        ExternalCrmTicket externalCrmTicket = new ExternalCrmTicket();
        externalCrmTicket.setCaseId(1);
        externalCrmTicket.setOriginalSystemName(CrmSystemName.STRAWBERRY);
        externalCrmTicket.setCustomerId(2);
        externalCrmTicket.setProvider("Test Provider");
        externalCrmTicket.setCreatedErrorCode(124);
        externalCrmTicket.setStatus(null);
        externalCrmTicket.setTicketCreationDate("04/12/2023 10:00");
        externalCrmTicket.setLastModifiedDate("04/12/2023 10:30");
        externalCrmTicket.setProductName("Test Product");

        CrmTicket expectedCrmTicket = CrmTicket.builder()
                .originalCaseId(externalCrmTicket.getCaseId())
                .originalSystemName(externalCrmTicket.getOriginalSystemName())
                .customerId(externalCrmTicket.getCustomerId())
                .providerName(externalCrmTicket.getProvider())
                .errorCode(externalCrmTicket.getCreatedErrorCode())
                .status(CrmTicketStatus.NOT_DEFINED)
                .creationDate(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(externalCrmTicket.getTicketCreationDate()))
                .lastModifiedDate(new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(externalCrmTicket.getLastModifiedDate()))
                .productName(externalCrmTicket.getProductName())
                .build();

        CrmTicket actualCrmTicket = converter.apply(externalCrmTicket);

        assertEquals(expectedCrmTicket, actualCrmTicket);
    }
}