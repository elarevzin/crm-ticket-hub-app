package com.erevzin.crmtickethub.logic.aggregation;

import com.erevzin.crmtickethub.datamodel.CrmTicket;
import com.erevzin.crmtickethub.datamodel.CrmTicketStatus;
import com.erevzin.crmtickethub.datamodel.ExternalCrmTicket;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ExternalToCrmTicketConverter implements Function<ExternalCrmTicket, CrmTicket> {


    @Override
    public CrmTicket apply(ExternalCrmTicket externalCrmTicket) {
        return CrmTicket.builder()
                .originalCaseId(externalCrmTicket.getCaseId())
                .originalSystemName(externalCrmTicket.getOriginalSystemName())
                .customerId(externalCrmTicket.getCustomerId())
                .providerName(externalCrmTicket.getProvider())
                .errorCode(externalCrmTicket.getCreatedErrorCode())
                .status(getStatus(externalCrmTicket))
                .creationDate(CrmTicketDateConverter.convertDateString(externalCrmTicket.getTicketCreationDate()))
                .lastModifiedDate(CrmTicketDateConverter.convertDateString(externalCrmTicket.getLastModifiedDate()))
                .productName(externalCrmTicket.getProductName())
                .build();
    }

    private CrmTicketStatus getStatus(ExternalCrmTicket externalCrmTicket) {
        if (externalCrmTicket.getStatus() != null) {
            return CrmTicketStatus.valueOf(externalCrmTicket.getStatus().toUpperCase());
        }
        return CrmTicketStatus.NOT_DEFINED;
    }

}
