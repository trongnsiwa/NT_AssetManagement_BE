package com.nashtech.rootkies.controllers.admin;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.constants.SuccessCode;
import com.nashtech.rootkies.converter.CategoryConverter;
import com.nashtech.rootkies.dto.category.CategoryDTO;
import com.nashtech.rootkies.dto.category.CreateCategoryDTO;
import com.nashtech.rootkies.dto.common.ResponseDTO;
import com.nashtech.rootkies.exception.CreateDataFailException;
import com.nashtech.rootkies.exception.DataNotFoundException;
import com.nashtech.rootkies.model.Category;
import com.nashtech.rootkies.service.CategoryService;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/category")
@Api( tags = "Category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryConverter categoryConverter;

    @ResponseBody
    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getCategoryList() throws DataNotFoundException {
        ResponseDTO response = new ResponseDTO();

        List<CategoryDTO> listDTO = categoryService.getListAllCategory();
        response.setData(listDTO);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createCategory(@Valid @RequestBody CreateCategoryDTO categoryDTO)
            throws CreateDataFailException {
        ResponseDTO response = new ResponseDTO();

        List<String> validateCategory = categoryService.checkIfExistCategoryNameAndPrefix(categoryDTO);

        if (!CollectionUtils.isEmpty(validateCategory)) {
            response.setErrorCode(ErrorCode.ERR_CREATE_CATEGORY_FAIL);
            response.setData(validateCategory);
        } else {
            try {
                Category category = categoryService.createCategory(categoryDTO);
                CategoryDTO dto = categoryConverter.convertToDTO(category);
                response.setData(dto);
                response.setSuccessCode(SuccessCode.CATEGORY_CREATED_SUCCESS);
            } catch (Exception ex) {
                response.setErrorCode(ErrorCode.ERR_CREATE_CATEGORY_FAIL);
                throw new CreateDataFailException(ErrorCode.ERR_CREATE_CATEGORY_FAIL);
            }
        }
        return ResponseEntity.ok().body(response);
    }

}
