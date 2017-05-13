package com.xn.interfacetest.util;

import com.xn.common.utils.PageInfo;

@Deprecated
public class PageContext
    extends PageInfo
{
    private static final long serialVersionUID = -5153321141985174266L;

    private static ThreadLocal<PageContext> context = new ThreadLocal<PageContext>();

    public static PageContext getContext()
    {
        PageContext ci = context.get();
        if ( ci == null )
        {
            ci = new PageContext();
            context.set( ci );
        }
        return ci;
    }

    public static void removeContext()
    {
        context.remove();
    }

    protected void initialize()
    {

    }

}
