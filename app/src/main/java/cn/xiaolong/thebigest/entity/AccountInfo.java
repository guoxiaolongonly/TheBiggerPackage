package cn.xiaolong.thebigest.entity;

import okhttp3.Cookie;

/**
 * <实现思路大体如下，为每个帐号注册一个QQ号，然后把帐号录入进来，每个帐号绑定相应的签名Cookie，
 * 为每个QQ设置密码当小号使用，使用PC端工具登录QQ把QQ的Cookie手动录入，缓存Cookie到本地
 * QQ缓存Cookie过期更新QQ，饿了么缓存Cookie过期自动重新登录。>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/17 15:05
 */
public class AccountInfo {

    public Cookie qqSignCookie;//    phoneNumber,openid,sid 这个是QQ登录后提供给饿了么需要的cookie
    public Cookie platformCookie; //这个是饿了么手机号登录后的cookie
}
