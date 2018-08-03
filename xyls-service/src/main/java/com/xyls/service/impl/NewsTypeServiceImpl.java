package com.xyls.service.impl;
import com.xyls.dto.form.NewsTypeForm;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import com.xyls.model.NewsType;
import com.xyls.repository.NewsTypeRepository;
import com.xyls.service.NewsTypeService;
import com.xyls.utils.DateUtil;
import com.xyls.utils.GenKeyUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@Transactional
public class NewsTypeServiceImpl implements NewsTypeService {

    @Autowired
    private NewsTypeRepository newsTypeRepository;

    @Override
    public Page<NewsType> query(Pageable pageable) {
        Page<NewsType>  page  = newsTypeRepository.findAll(pageable);
        return page;
    }

    @Override
    public void save(NewsTypeForm newsTypeForm, String useName) throws InvocationTargetException, IllegalAccessException {
        NewsType newsType = new NewsType();
        BeanUtils.copyProperties(newsType,newsTypeForm);
        newsType.setNewsTypeId(GenKeyUtil.key());
        newsType.setCreateTime(DateUtil.todayDateTime());
        newsType.setCreatePerson(useName);
        newsTypeRepository.save(newsType);
    }

    @Override
    public void remove(String ids) {
        if(StringUtils.isEmpty(ids)){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"id为空异常刷新页面重试！");
        }
        for(String item:ids.split(",") ){
            newsTypeRepository.delete(item);
        }
    }

    @Override
    public void modify(NewsTypeForm newsTypeForm, String useName) throws InvocationTargetException, IllegalAccessException {
        if(StringUtils.isEmpty(newsTypeForm.getNewsTypeId())){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"所要修改的栏目id为空，请刷新页面重新提交！");
        }
        NewsType newsType =newsTypeRepository.findOne(newsTypeForm.getNewsTypeId());
        BeanUtils.copyProperties(newsType,newsTypeForm);

        newsType.setModifyPerson(useName);
        newsType.setModifyTime(DateUtil.todayDateTime());
        newsTypeRepository.save(newsType);
    }

    @Override
    public NewsType query(String id) {
        if(StringUtils.isEmpty(id)){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"id为空异常刷新页面重试！");
        }
        return   newsTypeRepository.findOne(id);
    }

    @Override
    public List<NewsType> queryAll() {
        return newsTypeRepository.findAll();
    }

    @Override
    public List<NewsType> findCategoryByClassId(String id) {
        if(StringUtils.isEmpty(id)){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"id为空异常刷新页面重试！");
        }
        return newsTypeRepository.findAllByNewsTypeParentId(id);
    }
}
