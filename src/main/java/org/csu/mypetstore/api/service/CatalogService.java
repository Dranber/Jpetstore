package org.csu.mypetstore.api.service;

import org.csu.mypetstore.api.common.CommonResponse;
import org.csu.mypetstore.api.entity.Category;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CatalogService {
    CommonResponse<List<Category>> getCategoryList();

    CommonResponse<Category> getCategory(String categoryId);
}
