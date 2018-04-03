package com.thinkive.common.util;

import java.util.UUID;

/**
 * @Description UUID工具类
 * @Author dengchangneng
 * @Create 2018-04-03-11:23
 **/
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
