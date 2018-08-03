package com.xyls.service.impl;
import com.xyls.dto.form.PostForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.model.Post;
import com.xyls.model.Video;
import com.xyls.repository.PostRepository;
import com.xyls.service.PostService;
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

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;


    @Override
    public ResultGrid list(int page, int size, String condition) {


        Page<Post> postPage=postRepository.findAll(new PageRequest(page, size,new Sort(Sort.Direction.DESC,"createTime","top")));

        return new ResultGrid(0,"",Integer.parseInt(String.valueOf(postPage.getTotalElements())),postPage.getContent());
    }

    @Override
    public void save(PostForm form, String id) throws InvocationTargetException, IllegalAccessException {

    }

    @Override
    public void modify(PostForm form, String id) throws InvocationTargetException, IllegalAccessException {

    }

    @Override
    public void remove(String id) {

    }

    @Override
    public Post findOne(String id) {
        return null;
    }

    @Transactional
    @Override
    public void top(String postId) {
        Post post = postRepository.findOne(postId);
        if(post.getTop().equals("0")){
            post.setTop("1");
        }else {
            post.setTop("0");
        }
        postRepository.save(post);
    }

    @Transactional
    @Override
    public void status(String postId) {
        Post post = postRepository.findOne(postId);
        if(post.getStatus().equals("0")){
            post.setStatus("1");
        }else {
            post.setStatus("0");
        }
        postRepository.save(post);
    }
}
