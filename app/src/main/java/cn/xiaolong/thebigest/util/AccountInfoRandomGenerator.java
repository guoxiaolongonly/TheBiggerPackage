package cn.xiaolong.thebigest.util;

import java.util.Random;

import cn.xiaolong.thebigest.entity.AccountInfo;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/10 11:24
 */
public class AccountInfoRandomGenerator {
    public static String [] headerUrls = {
            "http://img.duoziwang.com/2018/19/07111648933237.jpg",
            "http://img.duoziwang.com/2018/19/07111648933238.jpg",
            "http://img.duoziwang.com/2018/19/07111648933239.jpg",
            "http://img.duoziwang.com/2018/19/07111648933240.jpg",
            "http://img.duoziwang.com/2018/19/07111648933241.jpg",
            "http://img.duoziwang.com/2018/19/07111648933242.jpg",
            "http://img.duoziwang.com/2018/19/07111648933243.jpg",
            "http://img.duoziwang.com/2018/19/07111648933244.jpg",
            "http://img.duoziwang.com/2018/19/07111648933245.jpg",
            "http://img.duoziwang.com/2018/19/07111648933246.jpg",
            "http://img.duoziwang.com/2018/19/07111648933247.jpg",
            "http://img.duoziwang.com/2018/19/07111648933248.jpg",
    };
    public static String [] nickNames = {
            "小蛮腰",
            "小迷妹",
            "小柯柯",
            "郑先生",
            "郑老师",
            "静哥哥",
            "匆匆",
            "忘事如烟",
            "小镇",
            "失去",
            "黄牛"
    };
    private static Random random=new Random();
    public static void generate(AccountInfo accountInfo)
    {

        accountInfo.unionId="";
        accountInfo.headerurl=headerUrls[random.nextInt(headerUrls.length)];
        accountInfo.nickname=nickNames[random.nextInt(nickNames.length)];
    }
}
