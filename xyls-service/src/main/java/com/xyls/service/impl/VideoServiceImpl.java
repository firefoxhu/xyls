package com.xyls.service.impl;

import com.xyls.dto.form.VideoForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import com.xyls.model.Video;
import com.xyls.repository.VideoRepository;
import com.xyls.service.VideoService;
import com.xyls.utils.DateUtil;
import com.xyls.utils.GenKeyUtil;
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

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Override
    public ResultGrid list(int page, int size, String condition) {

        Pageable pageRequest = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "createTime"));
        Specification<Video> specification = null;

        if (condition != null) {
            specification = (root, criteriaQuery, criteriaBuilder) -> {
                Path<String> _time = root.get("title");
                Predicate _key = criteriaBuilder.like(_time, "%" + condition + "%");
                return criteriaBuilder.and(_key);
            };
        }
        Page<Video> videoPage = videoRepository.findAll(specification, pageRequest);

        return new ResultGrid(0, "", Integer.parseInt(String.valueOf(videoPage.getTotalElements())), videoPage.getContent());
    }

    @Transactional
    @Override
    public void save(VideoForm form, String id) throws InvocationTargetException, IllegalAccessException {
        Video video = new Video();

        BeanUtils.copyProperties(video, form);

        video.setId(GenKeyUtil.key());

        video.setPlayNumber(String.valueOf(new Random().nextInt(100) + 1));
        video.setFabulous(String.valueOf(new Random().nextInt(20) + 1));
        video.setCreatePerson(id);
        video.setCreateTime(DateUtil.todayDateTime());
        videoRepository.save(video);
    }

    @Transactional
    @Override
    public void modify(VideoForm form, String id) throws InvocationTargetException, IllegalAccessException {
        if (StringUtils.isEmpty(form.getId())) {
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(), "参数异常，请刷新页面重新提交！");
        }
        Video video = videoRepository.getOne(form.getId());
        BeanUtils.copyProperties(video, form);

        video.setModifyDescription(id);
        video.setModifyPerson(id);
        video.setModifyTime(DateUtil.todayDateTime());
        videoRepository.save(video);
    }

    @Transactional
    @Override
    public void remove(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(), "id为空异常刷新页面重试！");
        }
        for (String item : id.split(",")) {
            videoRepository.delete(item);
        }
    }

    @Override
    public Video findOne(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(), "id为空异常刷新页面重试！");
        }
        return videoRepository.findOne(id);
    }

}
