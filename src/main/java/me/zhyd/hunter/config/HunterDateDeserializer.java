package me.zhyd.hunter.config;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;
import me.zhyd.hunter.util.DateUtil;

import java.lang.reflect.Type;

/**
 * fastjson 的日期反序列化组件，适配大部分日期格式
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/26 19:14
 * @since 1.8
 */
public class HunterDateDeserializer extends DateDeserializer {
    public static final HunterDateDeserializer instance = new HunterDateDeserializer();

    public HunterDateDeserializer() {
    }

    protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        return (T) DateUtil.parse(val);
    }
}
