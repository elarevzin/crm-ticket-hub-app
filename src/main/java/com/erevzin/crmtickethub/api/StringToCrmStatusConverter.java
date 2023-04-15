package com.erevzin.crmtickethub.api;

import com.erevzin.crmtickethub.datamodel.CrmTicketStatus;
import org.springframework.core.convert.converter.Converter;

public class StringToCrmStatusConverter implements Converter<String, CrmTicketStatus> {
    @Override
    public CrmTicketStatus convert(String source) {
        return CrmTicketStatus.valueOf(source.toUpperCase());
    }
}
