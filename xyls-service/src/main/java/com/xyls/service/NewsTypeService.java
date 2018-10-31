package com.xyls.service;

import com.xyls.dto.form.NewsTypeForm;
import com.xyls.model.NewsClass;
import com.xyls.model.NewsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface NewsTypeService {


    /**
     * 后台获取所有的栏目信息
     *
     * @param pageable
     * @return
     */
    Page<NewsType> query(Pageable pageable);


    /**
     * 添加一条栏目信息
     */
    void save(NewsTypeForm newsTypeForm, String useName) throws InvocationTargetException, IllegalAccessException;


    /**
     * 删除一条或者多条信息
     *
     * @param ids
     */
    void remove(String ids);


    /**
     * 修改信息
     *
     * @param newsTypeForm
     * @param useName
     */
    void modify(NewsTypeForm newsTypeForm, String useName) throws InvocationTargetException, IllegalAccessException;

    /**
     * 根据id查找
     */
    NewsType query(String id);

    List<NewsType> queryAll();

    List<NewsType> findCategoryByClassId(String id);
}
