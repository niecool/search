package com.nie.dao;

import com.nie.model.pojo.Product;
import org.springframework.stereotype.Component;

import java.util.List;


public interface ProductDao {
    public List<Product> queryForList(ProductExample productExample);
    public List<Product> selectByExample(ProductExample productExample);
    public List<Long> selectIdByCondition(ProductExample productExample);
    public Long countByExample(ProductExample productExample);
    public Integer update(ProductExample productExample);
    public Integer delete(ProductExample productExample);
    public Integer query(ProductExample productExample);

    public List<Long> selectIdBySince(String since);


}
