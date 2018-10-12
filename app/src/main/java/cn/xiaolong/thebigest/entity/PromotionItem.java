package cn.xiaolong.thebigest.entity;

import java.util.List;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/11 10:33
 */
public class PromotionItem {
    //多少钱
    public String amount;
    //有效期
    public String expire_date;
    //可用范围
    public List<String> hongbao_variety;
    //是否是新用户！！？？？哦哦哦
    public boolean is_new_user;
    //未知
    public int item_type;
    //红包名称
    public String name;
    //手机号
    public String phone;
    //来源
    public String source;
    //母鸡
    public int sum_condition;
    //到期
    public String validity_periods;

    public PromotionItem() {
    }

    public PromotionItem(String amount, String expire_date, List<String> hongbao_variety, boolean is_new_user, int item_type, String name, String phone, String source, int sum_condition, String validity_periods) {
        this.amount = amount;
        this.expire_date = expire_date;
        this.hongbao_variety = hongbao_variety;
        this.is_new_user = is_new_user;
        this.item_type = item_type;
        this.name = name;
        this.phone = phone;
        this.source = source;
        this.sum_condition = sum_condition;
        this.validity_periods = validity_periods;
    }

    @Override
    public String toString() {
        return "PromotionItem{" +
                "amount='" + amount + '\'' +
                ", expire_date='" + expire_date + '\'' +
                ", hongbao_variety=" + hongbao_variety +
                ", is_new_user=" + is_new_user +
                ", item_type=" + item_type +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", source='" + source + '\'' +
                ", sum_condition=" + sum_condition +
                ", validity_periods='" + validity_periods + '\'' +
                '}';
    }
}
