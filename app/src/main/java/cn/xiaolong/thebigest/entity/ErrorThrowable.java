package cn.xiaolong.thebigest.entity;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/11 10:57
 */
public class ErrorThrowable extends Throwable {
    public int code;
    public ErrorThrowable(int code,String message) {
        super(message);
        this.code=code;
    }
}
