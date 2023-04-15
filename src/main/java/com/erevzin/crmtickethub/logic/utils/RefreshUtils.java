package com.erevzin.crmtickethub.logic.utils;

import java.util.Calendar;
import java.util.Date;

public class RefreshUtils {
    public static boolean isValidForRefresh (Date dateToCheck){
        Date currentDate = new Date();
        Calendar currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);

        if (getDatePlusMinutes(currentDate, -15).compareTo(dateToCheck) <= 0) {
            return false;
        } else {
            return true;
        }

    }

    public static Date getDatePlusMinutes(Date currentDate, int minutes) {
        Calendar fifteenDaysAgo = Calendar.getInstance();
        fifteenDaysAgo.setTime(currentDate);
        fifteenDaysAgo.add(Calendar.MINUTE, minutes);
        return fifteenDaysAgo.getTime();
    }
}
