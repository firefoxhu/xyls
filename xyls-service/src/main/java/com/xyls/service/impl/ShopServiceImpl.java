package com.xyls.service.impl;

import com.xyls.constant.TableConst;
import com.xyls.dto.form.ShopForm;
import com.xyls.enums.ResponseEnum;
import com.xyls.exception.UserFormException;
import com.xyls.model.Shop;
import com.xyls.rbac.domain.User;
import com.xyls.rbac.repository.UserRepository;
import com.xyls.repository.ShopRepository;
import com.xyls.service.ShopService;
import com.xyls.utils.DateUtil;
import com.xyls.utils.GenKeyUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Page<Shop> query(Pageable pageable) {
        Page<Shop>  page  = shopRepository.findAll(pageable);
        return page;
    }

    @Override
    public void save(ShopForm shopForm, String userId) throws Exception {

        if (StringUtils.isEmpty(shopForm.getMobilePhone())) {
            throw new Exception("请输入门店关联的商户账号（手机/账号）！");
        }

        //验证用户信息是否存在
        User user = userRepository.findByUserName(shopForm.getMobilePhone());
        if (StringUtils.isEmpty(user)) {
            throw new Exception("门店关联的用户不存在！请先创建用户信息！");
        }

        //验证门店是否已经存在
        Shop existShop = shopRepository.findByName(shopForm.getName());
        if (!StringUtils.isEmpty(existShop)) {
            throw new Exception("门店已存在！");
        }


        Shop shop = new Shop();

        BeanUtils.copyProperties(shop,shopForm);
        shop.setUserId(user.getUserId());
        shop.setId(GenKeyUtil.key());
        shop.setCreatePerson(userId);
        shop.setCreateDescription("创建门店");
        shop.setCreateTime(DateUtil.todayDateTime());
        shop.setStatus(TableConst.NORMAL);
        shopRepository.save(shop);
    }

    @Override
    public void remove(String ids) {
        if(org.apache.commons.lang3.StringUtils.isEmpty(ids)){
            throw new UserFormException(ResponseEnum.ILLEGAL_PARAMS.getCode(),"id为空异常刷新页面重试！");
        }
        for(String item:ids.split(",") ){
            shopRepository.delete(item);
        }
    }

    @Override
    public void modify(ShopForm shopForm, String userId) throws Exception {

        if (StringUtils.isEmpty(shopForm.getId())) {
            throw new Exception("参数非法请刷新页面重试！");
        }


        Shop shop =shopRepository.findOne(shopForm.getId());

        BeanUtils.copyProperties(shop,shopForm);
        shop.setModifyTime(DateUtil.todayDateTime());
        shop.setModifyPerson(userId);
        shop.setModifyDescription("修改门店信息");
        shopRepository.save(shop);
    }

    @Override
    public Shop query(String id) {
        return shopRepository.findOne(id);
    }
}
