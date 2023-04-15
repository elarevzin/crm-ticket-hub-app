package com.erevzin.crmtickethub.logic.aggregation;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CrmTicketDateConverterTest {

    @Test
    public void testConvertDateString() {
        String dateString = "01/01/2022 10:30";
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Date expectedDate = null;
        try {
            expectedDate = formatter.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date actualDate = CrmTicketDateConverter.convertDateString(dateString);

        assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testConvertDateStringWithInvalidFormat() {
        String dateString = "2022/01/01 10:30";

        Date actualDate = CrmTicketDateConverter.convertDateString(dateString);

        assertNotNull(actualDate);
        assertInstanceOf(Date.class, actualDate);
    }

}