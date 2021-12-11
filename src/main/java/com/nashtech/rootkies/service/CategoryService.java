package com.nashtech.rootkies.service;

import com.nashtech.rootkies.dto.category.CategoryDTO;
import com.nashtech.rootkies.dto.category.CreateCategoryDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getListAllCategory() throws DataNotFoundException;

    Category createCategory(CreateCategoryDTO categoryDTO) throws CreateDataFailException;

    List<String> checkIfExistCategoryNameAndPrefix(CreateCategoryDTO categoryDTO);

}
