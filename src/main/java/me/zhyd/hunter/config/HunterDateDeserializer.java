package me.zhyd.hunter.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import me.zhyd.hunter.util.DateUtil;

import java.lang.reflect.Type;

/**
 * fastjson 的日期反序列化组件，适配大部分日期格式
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class HunterDateDeserializer implements ObjectDeserializer {
    public static final HunterDateDeserializer instance = new HunterDateDeserializer();

    public HunterDateDeserializer() {
    }

    protected <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        return (T) DateUtil.parse(val);
    }

    @Override
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        JSONObject object = JSONObject.parseObject(defaultJSONParser.getInput());
        if (null != o && o.equals("releaseDate")) {
            return (T) DateUtil.parse(object.get(o));
        }
        return (T) object.get(o);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
