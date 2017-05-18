package com.awesometickets.web.controller.response;

import java.util.HashMap;
import java.util.Map;


/**
 * Store custom error codes and their status code and information.
 */
public class ErrorStatus {

    public static final int BAD_REQUEST = 0;
    public static final int RESOURCE_NOT_FOUND = 1;
    public static final int PHONE_INVALID_FORMAT = 100;
    public static final int SMS_SEND_FAIL = 101;
    public static final int SMS_MISMATCH = 102;
    public static final int SEAT_UNAVAILABLE = 200;
    public static final int SEAT_NOT_FOUND = 201;
    public static final int USER_NOT_FOUND = 202;
    public static final int PURCHASE_UNAVAILABLE = 203;
    public static final int PHONE_MISMATCH = 300;
    public static final int TICKET_CODE_NOT_FOUND = 301;
    public static final int TICKET_CHECKED = 302;
    public static final int PASSWORD_INVALID_FORMAT = 400;
    public static final int PHONE_REGISTERED = 401;
    public static final int PASSWORD_MISMATCH = 402;
    public static final int SESSION_NOT_FOUND = 403;

    private static Map<Integer, String> infoMap;
    private static Map<Integer, Integer> statusMap;

    /**
     * Return the information of the custom error code.
     *
     * @param code The custom error code.
     */
    public static String info(int code) {
        return infoMap.get(code);
    }

    /**
     * Return the http status code of the custom error code.
     *
     * @param code The custom error code.
     */
    public static int status(int code) {
        return (Integer) statusMap.get(code);
    }

    /**
     * Initialize data.
     */
    public static void init() {
        infoMap = new HashMap<Integer, String>();
        statusMap = new HashMap<Integer, Integer>();
        infoMap.put(BAD_REQUEST, "Invalid parameters.");
        statusMap.put(BAD_REQUEST, 400);
        infoMap.put(RESOURCE_NOT_FOUND, "Resource not found.");
        statusMap.put(RESOURCE_NOT_FOUND, 404);
        infoMap.put(PHONE_INVALID_FORMAT, "Invalid phone number. Require 11 numbers.");
        statusMap.put(PHONE_INVALID_FORMAT, 400);
        infoMap.put(SMS_SEND_FAIL, "Fail to send sms code.");
        statusMap.put(SMS_SEND_FAIL, 400);
        infoMap.put(SMS_MISMATCH, "Incorrect sms code.");
        statusMap.put(SMS_MISMATCH, 400);
        infoMap.put(SEAT_UNAVAILABLE, "Seat already taken.");
        statusMap.put(SEAT_UNAVAILABLE, 400);
        infoMap.put(SEAT_NOT_FOUND, "Seat not found.");
        statusMap.put(SEAT_NOT_FOUND, 400);
        infoMap.put(USER_NOT_FOUND, "User not registered.");
        statusMap.put(USER_NOT_FOUND, 400);
        infoMap.put(PURCHASE_UNAVAILABLE, "The user with this phone cannot buy more tickets today.");
        statusMap.put(PURCHASE_UNAVAILABLE, 403);
        infoMap.put(PHONE_MISMATCH, "Incorrect phone number.");
        statusMap.put(PHONE_MISMATCH, 400);
        infoMap.put(TICKET_CODE_NOT_FOUND, "Ticket code not found.");
        statusMap.put(TICKET_CODE_NOT_FOUND, 400);
        infoMap.put(TICKET_CHECKED, "Ticket already checked.");
        statusMap.put(TICKET_CHECKED, 400);
        infoMap.put(PASSWORD_INVALID_FORMAT, "Invalid password format.");
        statusMap.put(PASSWORD_INVALID_FORMAT, 400);
        infoMap.put(PHONE_REGISTERED, "The phone is registered. Please use another phone.");
        statusMap.put(PHONE_REGISTERED, 403);
        infoMap.put(PASSWORD_MISMATCH, "Incorrect password.");
        statusMap.put(PASSWORD_MISMATCH, 403);
        infoMap.put(SESSION_NOT_FOUND, "Session not found. Maybe you haven't login.");
        statusMap.put(SESSION_NOT_FOUND, 403);
    }
}
