package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.csu.mypetstore.api.persistence.ProductMapper;
import org.csu.mypetstore.api.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public CommonResponse<List<Category>> getCategoryList() {

        List<Category> categoryList = categoryMapper.selectList(null);
        if(categoryList.isEmpty()) {
            return CommonResponse.creatForSuccessMessage("没有分类信息");
        }
        return CommonResponse.creatForSuccess(categoryList);
    }

    @Override
    public CommonResponse<Category> getCategory(String categoryId) {

        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            return CommonResponse.creatForSuccessMessage("没有该Id的Category");
        }
        return CommonResponse.creatForSuccess(category);
    }

    @Override
    public CommonResponse<List<Product>> getProductListByCategoryId(String categoryId) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", categoryId);

        List<Product> productList = productMapper.selectList(queryWrapper);
        if (productList.isEmpty()) {
            return CommonResponse.creatForSuccessMessage("该分类下没有product信息");
        }
        return CommonResponse.creatForSuccess(productList);
    }

    @Override
    public CommonResponse<Product> getProductById(String productId) {

        Product product = productMapper.selectById(productId);
        if (product == null) {
            return CommonResponse.creatForSuccessMessage("没有该Id的product");
        }
        return CommonResponse.creatForSuccess(product);
    }


}
