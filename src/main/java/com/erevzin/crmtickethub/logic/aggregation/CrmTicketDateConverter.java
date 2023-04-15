package com.erevzin.crmtickethub.logic.aggregation;


import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
public class CrmTicketDateConverter {

    public static Date convertDateString(String dateString)  {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date date;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            log.error("Failed to convert date : " + dateString + e.getMessage());
            date = new Date();
        }
        return date;
    }
}
