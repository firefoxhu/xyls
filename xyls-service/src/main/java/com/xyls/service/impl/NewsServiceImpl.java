package com.xyls.service.impl;

import com.xyls.model.News;
import com.xyls.repository.NewsRepository;
import com.xyls.dto.dto.NewsDTO;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import com.xyls.dto.form.NewsForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.service.NewsService;
import com.xyls.utils.DateUtil;
import com.xyls.utils.GenKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
@Service
@Transactional
@Slf4j
public class NewsServiceImpl implements NewsService {


    @Autowired
    private NewsRepository newsRepository;

    @Override
    public ResultGrid list(int page, int pageSize,String title) {

        Pageable pageRequest = new PageRequest(page, pageSize,new Sort(Sort.Direction.DESC,"createTime"));
        Specification<News> specification =null;

        if(title != null){
            specification = (root,criteriaQuery,criteriaBuilder)-> {
                    Path<String> _time = root.get("newsTitle");
                    Predicate _key = criteriaBuilder.like(_time,"%"+title+"%");
                    return criteriaBuilder.and(_key);
            };
        }

        Page<News> newsPage=newsRepository.findAll(specification,pageRequest);

        return new ResultGrid(0,"",Integer.parseInt(String.valueOf(newsPage.getTotalElements())),newsPage.getContent());
    }

    @Override
    public void save(NewsForm newsForm, String userId) throws InvocationTargetException, IllegalAccessException {
        News  news=new News();

        BeanUtils.copyProperties(news,newsForm);

        news.setNewsId(GenKeyUtil.key());

        news.setNewsViews(String.valueOf(new Random().nextInt(100)+1));
        news.setFabulous(String.valueOf(new Random().nextInt(20)+1));
        news.setCreatePerson(userId);
        news.setCreateTime(DateUtil.todayDateTime());
        newsRepository.save(news);
    }

    @Override
    public NewsDTO preview(String newsId) throws InvocationTargetException, IllegalAccessException {
        NewsDTO newsDTO=new NewsDTO();
        News  news=new News();
        news.setNewsId(newsId);
        news=newsRepository.findOne(newsId);

        BeanUtils.copyProperties(newsDTO,news);

        return newsDTO;
    }

    @Override
    public void modify(NewsForm newsForm, String userId) throws InvocationTargetException, IllegalAccessException {
        if(StringUtils.isEmpty(newsForm.getNewsId())){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"所要修改的新闻id为空，请刷新页面重新提交！");
        }
        News  news=newsRepository.getOne(newsForm.getNewsId());
        BeanUtils.copyProperties(news,newsForm);

        news.setModifyDescription(userId);
        news.setModifyPerson(userId);
        news.setModifyTime(DateUtil.todayDateTime());
       newsRepository.save(news);
    }

    @Override
    public void remove(String newsIds) {

        if(StringUtils.isEmpty(newsIds)){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"id为空异常刷新页面重试！");
        }
        for(String item:newsIds.split(",") ){
            newsRepository.delete(item);
        }
    }


}
