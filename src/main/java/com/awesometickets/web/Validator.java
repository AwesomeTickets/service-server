package com.awesometickets.web;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validation of all request parameters.
 */
@Component
public class Validator {
    /**
     * China TELECOM phone number
     * at 133,153,180,181,189,177,1700,173
     * **/
    private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|7[37]|8[019])\\d{8}$)|(^1700\\d{7}$)";

    /**
     * China UNICOM phone number
     * at 130,131,132,155,156,185,186,145,176,1707,1708,1709
     * **/
    private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^170[7-9]\\d{7}$)";

    /**
     * China MOBILE phone number
     * at 134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178,1705
     *
     **/
    private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

    /**
     * All phone number
     */
    private static final String PHONE_PATTERN=new StringBuilder().append(CHINA_MOBILE_PATTERN)
            .append("|")
            .append(CHINA_TELECOM_PATTERN)
            .append("|")
            .append(CHINA_UNICOM_PATTERN)
            .toString();

    private static final Pattern isPhonePattern = Pattern.compile(PHONE_PATTERN);

    /**
     * Check if the phone number is valid.
     *
     * @param phoneNum The phone number to be checked
     * @return True if the phone number is valid
     */
    public boolean isValidPhone(String phoneNum) {
        boolean flag = false;
        try {
            Matcher m = isPhonePattern.matcher(phoneNum);
            flag = m.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Check the seats format.
     *
     * @param seats The seats list to be checked
     * @return True if the seats array is valid
     */
    public boolean isValidSeats(Integer[] seats) {
        return seats.length <= 8 && seats.length >= 2 && seats.length % 2 == 0;
    }
}
