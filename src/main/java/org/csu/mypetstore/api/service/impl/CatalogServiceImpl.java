package org.csu.mypetstore.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Item;
import org.csu.mypetstore.api.entity.ItemInventory;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.csu.mypetstore.api.persistence.ItemInventoryMapper;
import org.csu.mypetstore.api.persistence.ItemMapper;
import org.csu.mypetstore.api.persistence.ProductMapper;
import org.csu.mypetstore.api.service.CatalogService;
import org.csu.mypetstore.api.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemInventoryMapper itemInventoryMapper;

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

    @Override
    public CommonResponse<List<ItemVO>> getItemsByProductId(String productId) {

        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productid", productId);
        List<Item> itemList = itemMapper.selectList(queryWrapper);
        if(itemList.isEmpty()) {
            return CommonResponse.creatForSuccessMessage("该product下没有item商品");
        }

        Product product = productMapper.selectById(productId);
        List<ItemVO> itemVOList = new ArrayList<>();
        for (Item item : itemList) {
            ItemVO itemVO = itemToItemVO(item, product);
            itemVOList.add(itemVO);
        }
        return CommonResponse.creatForSuccess(itemVOList);
    }

    private ItemVO itemToItemVO(Item item, Product product) {

        ItemVO itemVO = new ItemVO();
        itemVO.setItemId(item.getItemId());
        itemVO.setProductId(item.getProductId());
        itemVO.setListPrice(item.getListPrice());
        itemVO.setUnitcost(item.getUnitcost());
        itemVO.setSupplierId(item.getSupplierId());
        itemVO.setStatus(item.getStatus());
        itemVO.setAttribute1(item.getAttribute1());
        itemVO.setAttribute2(item.getAttribute2());
        itemVO.setAttribute3(item.getAttribute3());
        itemVO.setAttribute4(item.getAttribute4());
        itemVO.setAttribute5(item.getAttribute5());

        itemVO.setCategoryId(product.getCategoryId());
        itemVO.setProductName(product.getName());
        itemVO.setProductDescription(product.getDescription());

        ItemInventory itemInventory = itemInventoryMapper.selectById(item.getItemId());
        itemVO.setQuantity(itemInventory.getQuantity());

        return itemVO;
    }


}
