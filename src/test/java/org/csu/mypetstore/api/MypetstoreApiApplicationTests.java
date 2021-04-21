package org.csu.mypetstore.api;

import org.csu.mypetstore.api.entity.Category;
import org.csu.mypetstore.api.entity.Product;
import org.csu.mypetstore.api.persistence.CategoryMapper;
import org.csu.mypetstore.api.persistence.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MypetstoreApiApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Test
    void contextLoads() {
//        List<Category> categoryList = categoryMapper.selectList(null);
//        categoryList.forEach(System.out::println);
//        Category category = categoryMapper.selectById("Birds");
//        System.out.println(category.getDescription());
        Product product = productMapper.selectById("AV-CB-01");
        System.out.println(product.getProductId());
    }
}
