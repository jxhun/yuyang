package cn.com.yuyang.util;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Jxhun
 * Date: 2019/03/19
 * Description: ToKen 验证，生成Token
 * Version: V1.0
 */
public class ToKen {

    /**
     * 这个方法用来生成一个token
     *
     * @return 返回一个字符串作为token
     */
    public static String toKen() {
        // java系统默认生成的uuid，但是默认的uuid是有横杠隔开的，项目中需要手动去掉横杠
        return UUID.randomUUID().toString().replaceAll("-", "");  // 生成唯一随机码;
    }

}
