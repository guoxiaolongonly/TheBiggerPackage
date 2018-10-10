package cn.xiaolong.thebigest.entity;

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


    public String sign;  //eleme_key
    public String openId; //openId
    public String cookie; //cookie
    public String QQ; //作为QQ号码判断
    public String sid = "KPli0UVohktewRYTEA2Rkt4zKabfk6kHXUDQ"; //手机号码登录后会有一个sid
    public boolean isValid = true; //作为有效性判断
    public String time;//领取的日期
    public int perDaycount;//当日领取的次数
    public int allTimeCount;//总次数
    public String user_id = "20475540";
    public String headerurl;
    public String nickname;
    public String unionId;
    public String phoneNumber;


    public AccountInfo() {
    }

    public AccountInfo(String cookie,String sign, String openId, String QQ) {
        this.cookie=cookie;
        this.sign = sign;
        this.openId = openId;
        this.QQ = QQ;
    }
}
