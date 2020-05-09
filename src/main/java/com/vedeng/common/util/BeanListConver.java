package com.vedeng.common.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @program: erp
 * @description: 对象赋值
 * @author: addis
 * @create: 2020-03-30 16:14
 **/
public class BeanListConver {

    /**
     * @param input 输入集合
     * @param clzz  输出集合类型
     * @param <E>   输入集合类型
     * @param <T>   输出集合类型
     * @return 返回集合
     */
    public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
        List<T> output = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(input)) {
            for (E source : input) {
                T target = BeanUtils.instantiate(clzz);
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }
}
