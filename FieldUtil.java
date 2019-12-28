
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * [
 * 调用关系:
 * 实现接口及父类:
 * 子类:
 * 内部类列表:
 * ]
 * </pre>
 *
 * @author 作者：李保珠
 * @since 1.0
 * @version 2019-10-17 李保珠
 */
public final class FieldUtil {

    /**
     * 构造函数
     */
    private FieldUtil() {

    }

    /**
     * @param field field
     * @return String
     */
    public static String fieldToProperty(String field) {
        if (null == field) {
            return "";
        }
        String[] array = field.split("_");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            String seg = array[i];
            builder.append(StringUtils.capitalize(seg.toLowerCase()));
        }
        return StringUtils.uncapitalize(builder.toString());
    }

    /**
     * @param map map
     * @return map
     */
    public static Map<String, Object> fieldToPropertyForMap(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            result.put(fieldToProperty(key), value);
        }
        return result;
    }

    public static List<Map<String, Object>> fieldToPropertyForList(List<Map<String, Object>> list) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> item : list) {
            result.add(fieldToPropertyForMap(item));
        }
        return result;
    }

    /**
     * @param property property
     * @return String
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_");
            }
            sb.append(StringUtils.upperCase(CharUtils.toString(c)));
        }
        return sb.toString();
    }

    /**
     * 驼峰风格字符串转换为中划线间隔，如workWell转换为work-well
     *
     * @param property property
     * @return String
     */
    public static String propertyToStrikeStyle(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("-");
            }
            sb.append(StringUtils.upperCase(CharUtils.toString(c)));
        }
        return sb.toString().toLowerCase();
    }

}
