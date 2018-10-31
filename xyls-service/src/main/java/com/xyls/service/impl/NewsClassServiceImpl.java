package com.xyls.service.impl;

import com.xyls.dto.form.NewsClassForm;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import com.xyls.model.NewsClass;
import com.xyls.repository.NewsClassRepository;
import com.xyls.service.NewsClassService;
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
public class NewsClassServiceImpl implements NewsClassService {

    @Autowired
    private NewsClassRepository newsClassRepository;

    @Override
    public Page<NewsClass> query(Pageable pageable) {
        Page<NewsClass> page = newsClassRepository.findAll(pageable);
        return page;
    }

    @Override
    public void save(NewsClassForm newsClassForm, String useName) throws InvocationTargetException, IllegalAccessException {
        NewsClass newsClass = new NewsClass();

        BeanUtils.copyProperties(newsClass, newsClassForm);
        newsClass.setNewsClsId(GenKeyUtil.key());
        newsClass.setCreateTime(DateUtil.todayDateTime());
        newsClass.setCreatePerson(useName);
        newsClassRepository.save(newsClass);
    }

    @Override
    public void remove(String ids) {
        if (StringUtils.isEmpty(ids)) {
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(), "id为空异常刷新页面重试！");
        }
        for (String item : ids.split(",")) {
            newsClassRepository.delete(item);
        }
    }

    @Override
    public void modify(NewsClassForm newsClassForm, String useName) throws InvocationTargetException, IllegalAccessException {
        if (StringUtils.isEmpty(newsClassForm.getNewsClsId())) {
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(), "所要修改的栏目id为空，请刷新页面重新提交！");
        }
        NewsClass newsClass = newsClassRepository.findOne(newsClassForm.getNewsClsId());
        BeanUtils.copyProperties(newsClass, newsClassForm);
        newsClass.setModifyPerson(useName);
        newsClass.setModifyTime(DateUtil.todayDateTime());
        newsClassRepository.save(newsClass);
    }

    @Override
    public NewsClass query(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(), "id为空异常刷新页面重试！");
        }

        return newsClassRepository.findOne(id);
    }

    @Override
    public List<NewsClass> queryAll() {
        return newsClassRepository.findAll();
    }


}
