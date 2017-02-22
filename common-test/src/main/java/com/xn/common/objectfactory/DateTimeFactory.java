package com.xn.common.objectfactory;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang.StringUtils.isBlank;


public class DateTimeFactory extends InstanceFactory {

    //TODO quick fix
    private final static String[] FORMATS = new String[]{
            "yyyy-MM-dd hh:mm:ss",  //2012-01-01 01:01:01
            "yyyy-MM-dd HH:mm:ss",  //2012-01-01 15:01:01
            "yyyy-MM-dd",            //2012-01-01
            "yyyy-MM-dd HH:mm"      //2012-08-20 12:35
    };

    @Override
    protected Object create(Type type, Object value) {
        if (value == null) {
            return null;
        }
        String valueStr = value.toString();
        if (isBlank(valueStr)) {
            return null;
        }
        if (StringUtils.isNumeric(valueStr)) {
            return toDate(type, Long.valueOf(valueStr));
        }
        return toDate(type, valueStr);
    }

    private Object toDate(Type type, Long timestamp) {
        if (type.equals(java.sql.Date.class)) {
            return new java.sql.Date(timestamp);
        }
        if (type.equals(Timestamp.class)) {
            return new Timestamp(timestamp);
        }
        if (type.equals(Date.class)) {
            return new Date(timestamp);
        }
        if (type.equals(Time.class)) {
            return new Time(timestamp);
        }
        if (type.equals(Calendar.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(timestamp));
            calendar.setLenient(false);
            return calendar;
        }
        throw new RuntimeException("Can not cast long to given type:" + type);
    }

    private Object toDate(Type type, String timeStr) {
        if (type.equals(java.sql.Date.class)) {
            return java.sql.Date.valueOf(timeStr);
        }
        if (type.equals(Timestamp.class)) {
            return Timestamp.valueOf(timeStr);
        }
        if (type.equals(Time.class)) {
            return Time.valueOf(timeStr);
        }
        Calendar calendar = parse(timeStr);
        if (Calendar.class.isAssignableFrom((Class) type)) {
            return calendar;
        } else {
            return toDate(type, calendar.getTime().getTime());
        }
    }

    private Calendar parse(String timeStr) {
        RuntimeException lastException = null;
        for (int i = 0; i < FORMATS.length; ++i) {
            try {
                return parse(timeStr, getFormat(FORMATS[i]));
            } catch (RuntimeException e) {
                lastException = e;
            }
        }
        throw lastException;
    }

    private DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    private Calendar parse(String value, DateFormat format) {
        format.setLenient(false);
        ParsePosition pos = new ParsePosition(0);
        Date parsedDate = format.parse(value, pos);
        if (pos.getErrorIndex() >= 0 || pos.getIndex() != value.length() || parsedDate == null) {
            throw new RuntimeException("Can not format " + value + " to:" + format);
        }
        return format.getCalendar();
    }


    @Override
    public boolean support(Type type) {
        return type.equals(Date.class)
                || type.equals(java.sql.Date.class)
                || type.equals(Time.class)
                || type.equals(Timestamp.class)
                || type.equals(Calendar.class);
    }
}
