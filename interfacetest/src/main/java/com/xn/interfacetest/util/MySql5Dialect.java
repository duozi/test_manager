package com.xn.interfacetest.util;

/**
 * Created by xn058121 on 2017/5/11.
 */
public class MySql5Dialect extends Dialect
{
    protected static final String SQL_END_DELIMITER = ";";

    public String getLimitString( String sql, boolean hasOffset )
    {
        return MySql5PageHepler.getLimitString( sql, -1, -1 );
    }

    public String getLimitString( String sql, int offset, int limit )
    {
        return MySql5PageHepler.getLimitString( sql, offset, limit );
    }

    public boolean supportsLimit()
    {
        return true;
    }

}
