package com.xyls.service;

import com.xyls.dto.form.PostForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.model.Post;
import com.xyls.service.support.BaseService;

public interface PostService extends BaseService<PostForm, ResultGrid, Post> {

    void top(String postId);

    void status(String postId);


}
