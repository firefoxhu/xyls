package com.xyls.service;

import com.xyls.dto.dto.NewsDTO;
import com.xyls.dto.form.NewsForm;
import com.xyls.dto.support.ResultGrid;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface NewsService {


    /**
     * 后台查询新闻列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return
     */
    ResultGrid list(int page, int pageSize, String title);


    /**
     * 后台添加一条新闻信息
     *
     * @param newsForm
     * @param userId
     */
    void save(NewsForm newsForm, String userId) throws InvocationTargetException, IllegalAccessException;


    /**
     * 后台预览新闻数据
     *
     * @param newsId 新闻唯一id
     * @return
     */
    NewsDTO preview(String newsId) throws InvocationTargetException, IllegalAccessException;


    /**
     * 后台修改新闻信息
     *
     * @param newsForm 传入要修改的实体内容
     * @param userId   哪个用户操作的修改
     */
    void modify(NewsForm newsForm, String userId) throws InvocationTargetException, IllegalAccessException;


    /**
     * 后台删除资讯信息
     *
     * @param newsIds
     */
    void remove(String newsIds);


}
