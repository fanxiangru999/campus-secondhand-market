package com.campus.market.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * ID 生成工具类
 */
public class IdGenerator {

    private static final Random RANDOM = new Random();

    /**
     * 生成物品编号：P + yyyyMMdd + 4位随机数
     */
    public static String generateProductId() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomNum = RANDOM.nextInt(10000);
        return String.format("P%s%04d", date, randomNum);
    }

    /**
     * 生成交易编号：T + yyyyMMdd + 4位随机数
     */
    public static String generateTradeId() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomNum = RANDOM.nextInt(10000);
        return String.format("T%s%04d", date, randomNum);
    }

    /**
     * 生成留言编号：M + yyyyMMdd + 4位随机数
     */
    public static String generateMessageId() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomNum = RANDOM.nextInt(10000);
        return String.format("M%s%04d", date, randomNum);
    }
}
