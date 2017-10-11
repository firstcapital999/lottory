package com.thinkive.common.authority.util;

import org.springframework.context.ApplicationContext;

/**
 * Created by thinkive on 2017/10/9.
 */
public class Appctx {
    public static ApplicationContext ctx=null;
    public static Object getObject(String string){
        return ctx.getBean(string);
    }
}
