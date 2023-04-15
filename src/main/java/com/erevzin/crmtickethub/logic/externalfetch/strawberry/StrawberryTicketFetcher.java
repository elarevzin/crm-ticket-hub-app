package com.erevzin.crmtickethub.logic.externalfetch.strawberry;

import com.erevzin.crmtickethub.datamodel.ExternalCrmTicket;
import com.erevzin.crmtickethub.logic.externalfetch.CrmExternalTicketFetcher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("real")
public class StrawberryTicketFetcher implements CrmExternalTicketFetcher {
    @Override
    public List<ExternalCrmTicket> fetchCrmTickets() {
        //TODO: implement call rest API for strawberry service
        return List.of();
    }
}
