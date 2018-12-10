package cn.xiaolong.thebigest.util;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/19 11:44
 */
public class UrlParserHelper {

    public static String parserUrl(String redPackageUrl) {
        String parserResult = "";
        redPackageUrl.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        redPackageUrl = URLDecoder.decode(redPackageUrl);
        if (redPackageUrl.startsWith("https://") && redPackageUrl.contains("https://h5.ele.me/hongbao/")) {
            //说明应该是红包链接了
            Map<String, String> paramMap = new HashMap<>();
            String parmas = redPackageUrl.split("https://h5.ele.me/hongbao/")[1];
            String[] datas = parmas.split("&");
            for (String data : datas) {
                String[] param = data.split("=");
                if (param.length > 1) {
                    paramMap.put(param[0], param[1]);
                }
            }
            if (paramMap.containsKey("sn") && paramMap.containsKey("lucky_number")) {
                parserResult = "sn=" + paramMap.get("sn");
            }
        } else if (redPackageUrl.startsWith("https://h5.ele.me/grouping/")) {
            parserResult = "id=" + redPackageUrl.split("id=")[1].split("&")[0];
        }
        return parserResult;
    }
}
