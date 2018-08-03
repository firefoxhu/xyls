package com.xyls.repository;

import com.xyls.model.Shop;

public interface ShopRepository extends XylsRepository<Shop> {

    Shop findByName(String name);
}
