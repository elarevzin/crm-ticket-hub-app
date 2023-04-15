package com.erevzin.crmtickethub.logic.externalfetch.banana;

import com.erevzin.crmtickethub.datamodel.ExternalCrmTicket;
import com.erevzin.crmtickethub.logic.externalfetch.CrmExternalTicketFetcher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("real")
public class BananaTicketFetcher implements CrmExternalTicketFetcher {


    @Override
    public List<ExternalCrmTicket> fetchCrmTickets() {
        //TODO: implement call rest API for banana service
        return List.of();
    }
}
