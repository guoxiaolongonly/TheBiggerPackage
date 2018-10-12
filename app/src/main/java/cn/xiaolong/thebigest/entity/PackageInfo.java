package cn.xiaolong.thebigest.entity;

import java.util.List;

/**
 * <拿到的红包详情>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/11 10:31
 */
public class PackageInfo {
    //帐号~手机号
    public String account;
    //是不是大红包呀
    public boolean is_lucky;
    public boolean is_new_supervip;
    //母鸡，可能是第几个红包
    public int lucky_status;
    //这个是领取到的红包列表
    public List<PromotionItem> promotion_items;
    //用来判断已经领取的用户数量
    public List<String> promotion_records;
    //母鸡
    public int ret_code;

    @Override
    public String toString() {
        return "PackageInfo{" +
                "account='" + account + '\'' +
                ", is_lucky=" + is_lucky +
                ", is_new_supervip=" + is_new_supervip +
                ", lucky_status=" + lucky_status +
                ", promotion_items=" + promotion_items +
                ", promotion_records=" + promotion_records +
                ", ret_code=" + ret_code +
                '}';
    }
}
