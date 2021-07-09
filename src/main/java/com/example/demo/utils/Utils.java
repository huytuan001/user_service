package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static Timestamp getTimeNow(String format) {
        try {
            String timeNow = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
            return Utils.strToTimestamp(timeNow, format);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static Timestamp strToTimestamp(String str, String format) throws ParseException {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date parsedDate = dateFormat.parse(str);
        return new Timestamp(parsedDate.getTime());
    }

    public enum UserStatus {
        LOCK(-1),
        NOT_ACTIVE(0),
        ACTIVE(1),
        FORGET_PASSWORD(2);

        private final int value;

        UserStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum ReturnCode {
        FAIL(0),
        SUCCESS(1),
        EXCEPTION(2);

        private final int value;

        ReturnCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
