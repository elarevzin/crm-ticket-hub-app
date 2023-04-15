package com.erevzin.crmtickethub.logic;


import com.erevzin.crmtickethub.logic.utils.RefreshUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;


public class RefreshUtilsTest {

    @Test
    public void testIsValidForRefresh_RefreshNeeded() {
        Date currentDate = new Date();
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        currentCal.add(Calendar.MINUTE, -20);
        Date dateToCheck = currentCal.getTime();
        assertTrue(RefreshUtils.isValidForRefresh(dateToCheck));
    }

    @Test
    public void testIsValidForRefresh_RefreshNotNeeded() {
        Date currentDate = new Date();
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        currentCal.add(Calendar.MINUTE, -10);
        Date dateToCheck = currentCal.getTime();
        assertFalse(RefreshUtils.isValidForRefresh(dateToCheck));
    }

    @Test
    public void testGetDatePlusMinutes() {
        Date currentDate = new Date();
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        currentCal.add(Calendar.MINUTE, 10);
        Date expectedDate = currentCal.getTime();
        Date actualDate = RefreshUtils.getDatePlusMinutes(currentDate, 10);
        assertEquals(expectedDate, actualDate);
    }

}