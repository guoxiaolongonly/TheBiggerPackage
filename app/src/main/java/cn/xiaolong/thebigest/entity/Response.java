package cn.xiaolong.thebigest.entity;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/19 16:38
 */
public class Response<T> {
    public String code;
    public T data;
    public String message;
}
