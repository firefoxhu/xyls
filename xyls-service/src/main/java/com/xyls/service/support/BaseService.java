package com.xyls.service.support;

import java.lang.reflect.InvocationTargetException;

/**
 * @param <F> 表单数据 如ArticleForm 从前端做验证且通过  进一步包装为我们需要存储的数据对象
 * @param <R> 返回的结果对象
 */
public interface BaseService<F, R, S> {

    /**
     * 查询后台数据
     *
     * @param page      页码
     * @param size      每页数量
     * @param condition 条件
     * @return 对于前端layui的数据表结构
     */
    R list(int page, int size, String condition);


    /**
     * 后台添加一条新闻信息
     *
     * @param form
     */
    void save(F form, String id) throws InvocationTargetException, IllegalAccessException;


    /**
     * @param form 传入要修改的实体内容
     * @param id   哪个用户操作的修改
     */
    void modify(F form, String id) throws InvocationTargetException, IllegalAccessException;


    /**
     * @param id
     */
    void remove(String id);

    /**
     * @param id
     * @return
     */
    S findOne(String id);

}
