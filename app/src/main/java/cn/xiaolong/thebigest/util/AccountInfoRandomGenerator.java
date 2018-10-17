package cn.xiaolong.thebigest.util;

import java.util.Random;

import cn.xiaolong.thebigest.entity.AccountInfo;

/**
 * <这个是用来随机成头像和昵称的，如果想要自己配置就自己替换一下>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/10 11:24
 */
public class AccountInfoRandomGenerator {
    public static String [] headerUrls = {
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
            "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLSDjAia9GnU9JSI10zV3QnsLsjFqbwdMQxmOEwpuSeP8S7O2bJ01C4cqolDFWdT50L8y8DiaHJbWTw/132",
    };
    public static String [] nickNames = {
            "儒杰杰",
            "小龙",
            "小柯柯",
            "球球",
            "郑老师",
            "静哥哥",
            "熊熊",
            "陈婷",
            "小镇",
            "罗老师",
            "阿潘"
    };
    private static Random random=new Random();
    public static void generate(AccountInfo accountInfo)
    {

        accountInfo.unionId="";
        accountInfo.headerurl=headerUrls[random.nextInt(headerUrls.length)];
        accountInfo.nickname=nickNames[random.nextInt(nickNames.length)];
    }
}
