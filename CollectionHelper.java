package com.comtop.lcam.fwms.dataqualitycontrol.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者：李保珠
 * @version 2019-11-16 李保珠
 * @since 1.0
 */
public final class CollectionHelper {
    /**
     * 构造方法
     */
    private CollectionHelper() {

    }

    /**
     * 应用示例，给定一个馈线信息(馈线信息为dto或vo)集合，获取所有馈线id或名称
     *
     * @param list       数据集合
     * @param fieldToGet 要获取的字段
     * @param isDistinct 是否去重
     * @param propClass  属性类型
     * @param objClass   集合中元素类型
     * @param <T>        泛型
     * @param <E>        泛型
     * @return list
     */
    public static <T, E> List<T> getAllValueByprop(List<E> list, String fieldToGet, boolean isDistinct, Class<T> propClass, Class<E> objClass) {
        List<T> result = new ArrayList<>();
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldToGet, objClass);
            Method getMethod = pd.getReadMethod();
            for (E e : list) {
                T fieldVal = (T) getMethod.invoke(e);
                //如果需要去重，且已存在，则不再添加
                if (isDistinct && result.contains(fieldVal)) {
                    continue;
                }
                result.add(fieldVal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 应用示例，给定一个馈线信息(馈线信息为map)集合，获取所有馈线id或名称
     *
     * @param list       数据集合
     * @param keyToGet 要获取的字段
     * @param isDistinct 是否去重
     * @param valClass  值类型
     * @param <T>        泛型
     * @param <E>        泛型
     * @return list
     */
    public static <T, E> List<T> getAllValuesByKey(List<Map<String, Object>> list, String keyToGet, boolean isDistinct, Class<T> valClass) {
        List<T> result = new ArrayList<>();
        try {
            for (Map<String, Object> map : list) {
                T value = (T) map.get(keyToGet);
                //如果需要去重，且已存在，则不再添加
                if (isDistinct && result.contains(value)) {
                    continue;
                }
                result.add(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 应用示例：给定一个站房集合，需要以馈线id进行分类，把馈线id和属于该馈线的站房作为一个数据对返回，可调用此方法
     *
     * @param list        数据集合
     * @param filedForkey 用作key的字段
     * @param propClass   属性类型
     * @param valClass    值类型
     * @return map
     */
    public static <S> Map<S, List<Map<String,Object>>> sortByProp(List<Map<String, Object>> list, String filedForkey, Class<String> propClass, Class<S> valClass) {
        Map<S, List<Map<String,Object>>> result = new HashMap<>();
        try {
            for (Map<String, Object> e : list) {
                S fieldVal = (S)e.get(filedForkey);
                if(fieldVal == null){
                    continue;
                }
                List<Map<String,Object>> objList = result.get(fieldVal);
                if (objList == null) {
                    objList = new ArrayList<>();
                    result.put(fieldVal, objList);
                }
                objList.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 应用示例：给定一个元素集合，需要以id为key，以元素本身作为value组成一个map返回，可调用此方法
     *
     * @param list        数据集合
     * @param filedForkey 用作key的字段
     * @param propClass   属性类型
     * @param objClass    集合中元素类型
     * @param <K>         泛型
     * @param <E>         泛型
     * @return map
     */
    public static <K, E> Map<K, E> getPropAndObjMap(List<E> list, String filedForkey, Class<K> propClass, Class<E> objClass) {
        Map<K, E> result = new HashMap<>();
        try {
            PropertyDescriptor pd = new PropertyDescriptor(filedForkey, objClass);
            Method getMethod = pd.getReadMethod();
            for (E e : list) {
                K fieldVal = (K) getMethod.invoke(e);
                result.put(fieldVal, e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 应用示例：给定一个馈线集合，需要以id为key，馈线名称为value组成一个map返回，可调用此方法
     *
     * @param list        数据集合
     * @param filedForkey 用作key的字段
     * @param objClass    属性类型
     * @param prop1Class  元素中属性1类型
     * @param prop2Class  元素中属性2类型
     * @param <E>         泛型
     * @param <K>         泛型
     * @param <V>         泛型
     * @return map
     */
    public static <E, K, V> Map<K, V> getProp1AndProp2Map(List<E> list, String filedForkey, Class<E> objClass, Class<K> prop1Class, Class<V> prop2Class) {
        Map<K, V> result = new HashMap<>();
        try {
            PropertyDescriptor prop1Desc = new PropertyDescriptor(filedForkey, objClass);
            Method method1 = prop1Desc.getReadMethod();
            PropertyDescriptor prop2Desc = new PropertyDescriptor(filedForkey, objClass);
            Method method2 = prop2Desc.getReadMethod();
            for (E e : list) {
                K field1Val = (K) method1.invoke(e);
                V field2Val = (V) method2.invoke(e);
                result.put(field1Val, field2Val);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static <K, V> Map<K, V> getProp1AndProp2Map(List<Map<String, Object>> list, String filed1Forkey,  String filed2Forkey, Class<K> prop1Class, Class<V> prop2Class) {
        Map<K, V> result = new HashMap<>();
        try {

            for (Map<String,Object> e : list) {
                K field1Val = (K) e.get(filed1Forkey);
                V field2Val = (V) e.get(filed2Forkey);
                result.put(field1Val, field2Val);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
