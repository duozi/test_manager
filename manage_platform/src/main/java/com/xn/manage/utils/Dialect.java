package com.xn.manage.utils;

/**
 * Created by xn058121 on 2017/5/11.
 */
public class Dialect {
    public boolean supportsLimit()
    {
        return false;
    }

    public boolean supportsLimitOffset()
    {
        return supportsLimit();
    }

    public String getLimitString(String sql, int offset, int limit)
    {
        return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
    }

    public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
    {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    public static enum Type
    {
        MYSQL, ORACLE;
    }
}
