package com.goodsoft.society_zy.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 定时器类
 * Created by 龙宏 on 2017/11/23.
 */
public class MyTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void putDataToCache() {

        logger.info("Alert! putDataToCache........" + new Date());
    }
}
