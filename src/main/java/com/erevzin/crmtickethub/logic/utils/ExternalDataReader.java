package com.erevzin.crmtickethub.logic.utils;

import com.erevzin.crmtickethub.datamodel.ExternalCrmTicket;
import com.erevzin.crmtickethub.datamodel.ExternalCrmTicketsData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ExternalDataReader {

    public List<ExternalCrmTicket> getExternalCrmTicketList(String filePath) {
        TypeReference<ExternalCrmTicketsData> typeReference = new TypeReference<>() {};
        List<ExternalCrmTicket> externalCrmTicketList = new ArrayList<>();
        ExternalCrmTicketsData externalCrmTicketsData;
        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            externalCrmTicketsData = new ObjectMapper().readValue(inputStream, typeReference);
            if(!CollectionUtils.isEmpty(externalCrmTicketsData.getExternalCrmTicketList())) {
                externalCrmTicketList = externalCrmTicketsData.getExternalCrmTicketList();
            }
        } catch (IOException e){
            log.error("Failed to read file from path: " + filePath + ". Reason: " + e.getMessage());
        }
        return externalCrmTicketList;
    }
}
