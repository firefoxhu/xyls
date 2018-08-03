package com.xyls.repository;

import com.xyls.model.NewsType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsTypeRepository extends XylsRepository<NewsType> {

    List<NewsType> findAllByNewsTypeParentId(String id);
}
