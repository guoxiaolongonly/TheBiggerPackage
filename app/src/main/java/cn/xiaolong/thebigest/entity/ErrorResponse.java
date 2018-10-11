package cn.xiaolong.thebigest.entity;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/11 10:44
 */
public class ErrorResponse {

    //状态名称
    public String message;

    //状态信息
    public String name;

    public int errorCode;

    public ErrorResponse(String message, String name, int code) {
        this.message = message;
        this.name = name;
        this.errorCode = code;
    }
}
