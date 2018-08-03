package com.xyls.service;
import com.xyls.dto.form.ShopForm;
import com.xyls.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface ShopService {

    /**
     * 后台获取所有的门店信息
     * @param pageable
     * @return
     */
    Page<Shop> query(Pageable pageable);

    /**
     * 添加一条栏目信息
     */
    void  save(ShopForm shopForm, String userId) throws Exception;


    /**
     * 删除一条或者多条信息
     * @param ids
     */
    void  remove(String ids);


    /**
     * 修改信息
     */
    void  modify(ShopForm shopForm, String userId) throws Exception;

    /**
     * 根据id查找
     */
    Shop query(String id);
}
