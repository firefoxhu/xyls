package com.xyls.repository;

import com.xyls.model.News;
import com.xyls.model.NewsType;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends XylsRepository<News> {

}
