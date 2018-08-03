package com.xyls.service;
import com.xyls.dto.form.VideoForm;
import com.xyls.dto.support.ResultGrid;
import com.xyls.model.Video;
import com.xyls.service.support.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface VideoService extends BaseService<VideoForm,ResultGrid,Video> {
}
