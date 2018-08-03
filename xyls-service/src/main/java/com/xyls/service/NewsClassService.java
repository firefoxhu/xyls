package com.xyls.service;

import com.xyls.dto.form.NewsClassForm;
import com.xyls.model.NewsClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public interface NewsClassService {


    /**
     * 后台获取所有的栏目信息
     * @param pageable
     * @return
     */
    Page<NewsClass> query(Pageable pageable);


    /**
     * 添加一条栏目信息
     */
    void  save(NewsClassForm newsClassForm,String useName) throws InvocationTargetException, IllegalAccessException;


    /**
     * 删除一条或者多条信息
     * @param ids
     */
    void  remove(String ids);


    /**
     * 修改信息
     * @param newsClassForm
     * @param useName
     */
    void  modify(NewsClassForm newsClassForm,String useName) throws InvocationTargetException, IllegalAccessException;

    /**
     * 根据id查找
     */
    NewsClass  query(String id);


    /**
     *获取所有得栏目
     * @return
     */
    List<NewsClass>  queryAll();




}
