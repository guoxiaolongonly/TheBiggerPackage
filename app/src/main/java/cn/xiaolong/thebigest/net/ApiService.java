package cn.xiaolong.thebigest.net;


import cn.xiaolong.thebigest.entity.ResponseHeader;
import cn.xiaolong.thebigest.entity.TokenBean;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/9/19 14:18
 */
public interface ApiService {
    /**
     * 绑定相关
     */

    /**
     * 这个接口是发送手机验证码的接口
     * @param mobile 手机号
     * @param captchaHash 图形验证码标识，饿了么的图形验证码机制，
     *                    如果要做这个需要拿到验证码标识和图片做图像处理解析验证码。
     * @param captchaValue 图形验证码内容
     * @return 返回参数是验证码Token validateToken
     */
    @FormUrlEncoded
    @POST("/restapi/eus/login/mobile_send_code")
    Observable<TokenBean> getMobileCode(@Field("mobile") String mobile, @Field("captcha_hash") String captchaHash
            , @Field("captcha_value") String captchaValue);


    /**
     * 这个接口是登录的接口
     *  请求亲需要带上
     * @param mobile 手机号
     * @param validateToken 由验证码接口获取到的token
     * @param validateCode 手机接收到的验证码信息
     * @return 返回参数是user_id 这个接口需要拦截掉，获取到Response头中的sid。
     */
    @FormUrlEncoded
    @POST("/restapi/eus/login/login_by_mobile")
    Observable<ResponseHeader> loginByMobile(@Field("mobile") String mobile, @Field("validate_token") String validateToken
            , @Field("validate_code") String validateCode);


    /**
     * 切换手机号码的接口，这个类要求拦截请求头，在请求的cookie中加上sid
     * @param openId QQ登录饿了么之后获取到的openid
     * @param mobile 手机号
     * @param sign 签名信息这个在QQ登陆后获取的Cookie里面有
     * @return
     */
    @FormUrlEncoded
    @POST("/restapi/marketing/hongbao/weixin/{openId}/change")
    Observable<ResponseBody> changeMobile(@Path("openId")String openId,@Field("phone") String mobile, @Field("sign") String sign);

    /**
     * 通过 sn码获取最大红包
     *
     * @param sn sn码来自红包链接中的 sn=xxx，可以通过解析url参数来获得
     * @return
     */
    @GET("/restapi/marketing/themes/0/group_sns/{sn}")
    Observable<ResponseBody> getLuckyNumber(@Path("sn") String sn);

    /**
     * 最激动人心的点红包环节,这个类要求拦截请求头，在请求的cookie中加上sid
     * 和"x-shard" x-shared 相当于把sn转成16进制int
     * @param openId 这个在QQ登录后获取
     * @param device_id 默认为空就好
     * @param sn  这个可以直接拿url获取
     * @param hardware_id 为空
     * @param phone 为空
     * @param platform  默认填0
     * @param sign 签名信息 就是登录后的sign
     * @param track_id 为空
     * @param unionid  可以固定随机下缓存到帐号信息里面就好
     * @param weixin_avatar  随机获取后缓存 //微信头像地址
     * @param weixin_username 微信用户昵称。
     * @return
     */
    @FormUrlEncoded
    @POST("/restapi/marketing/promotion/weixin/{openId}")
    Observable<ResponseBody> touchRedPackage(@Path("openId") String openId,
                                             @Path("device_id") String device_id,
                                             @Path("group_sn") String sn,
                                             @Path("hardware_id") String hardware_id,
                                             @Path("phone") String phone,
                                             @Path("platform") String platform,
                                             @Path("sign") String sign,
                                             @Path("track_id") String track_id,
                                             @Path("unionid") String unionid,
                                             @Path("weixin_avatar") String weixin_avatar,
                                             @Path("weixin_username") String weixin_username);

}
