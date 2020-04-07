package me.zhyd.hunter.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 解析器，针对每个平台，可以单独定制解析器，因为部分平台的部分内容，不是常规的html结构，可能为html中嵌套json结构
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
public class HunterResolver {
    /**
     * 解析器类型，可选：regex、xpath，默认为xpath，并且当type不等于regex时，直接取xpath
     */
    private String type;
    /**
     * 需要处理的字段类类型，一般为数字类型，比如java.lang.Long、java.lang.Integer、java.lang.Float、java.lang.Double
     */
    private String clazz;
    /**
     * 操作符，如果type=regex并且clazz=数字类型，则按照operator进行计算
     */
    private String operator;

    /**
     * 解析operator，转换为 计算符 和 数字
     *
     * @return map
     */
    public Map<String, Object> getOperatorMap() {
        String operator = this.operator;
        if (StringUtils.isEmpty(operator)) {
            return null;
        }
        String[] operatorArr = operator.split(" ");
        if (operatorArr.length < 2) {
            return null;
        }
        Map<String, Object> res = new HashMap<>();
        res.put("operator", operatorArr[0]);
        res.put("num", operatorArr[1]);
        return res;
    }
}
