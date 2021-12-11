package com.nashtech.rootkies.service.impl;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.converter.CategoryConverter;
import com.nashtech.rootkies.dto.category.CategoryDTO;
import com.nashtech.rootkies.dto.category.CreateCategoryDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.Category;
import com.nashtech.rootkies.repository.CategoryRepository;
import com.nashtech.rootkies.service.CategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryConverter categoryConverter;

    @Override
    public List<CategoryDTO> getListAllCategory() throws DataNotFoundException {
        List<CategoryDTO> listDTO;

        try {
            Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name");
            List<Category> listEntity = categoryRepository.findAll(Sort.by(order));
            listDTO = categoryConverter.toDTOList(listEntity);
        } catch (Exception ex) {
            LOGGER.info("Having error when loading the category list: " + ex.getMessage());
            throw new DataNotFoundException(ErrorCode.ERR_CATEGORY_LIST_LOADED_FAIL);
        }

        return listDTO;
    }

    @Override
    public List<String> checkIfExistCategoryNameAndPrefix(CreateCategoryDTO categoryDTO) {
        List<String> errorList = new ArrayList<>();

        Boolean checkName = categoryRepository.existsByName(categoryDTO.getName());

        Boolean checkPrefix = categoryRepository.existsByPrefix(categoryDTO.getPrefix());

        if (checkName) {
            errorList.add(ErrorCode.ERR_CATEGORY_NAME_EXISTED);
        }

        if (checkPrefix) {
            errorList.add(ErrorCode.ERR_PREFIX_EXISTED);
        }

        return errorList;
    }

    @Override
    public Category createCategory(CreateCategoryDTO categoryDTO) throws CreateDataFailException {
        Category category;

        try {
            category = categoryConverter.convertToEntity(categoryDTO);
            return categoryRepository.save(category);
        }catch (Exception ex) {
            throw new CreateDataFailException(ErrorCode.ERR_CREATE_CATEGORY_FAIL);
        }
    }

}
