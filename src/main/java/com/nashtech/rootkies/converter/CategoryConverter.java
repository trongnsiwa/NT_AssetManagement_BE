package com.nashtech.rootkies.converter;

import com.nashtech.rootkies.constants.ErrorCode;
import com.nashtech.rootkies.dto.category.CategoryDTO;
import com.nashtech.rootkies.dto.category.CreateCategoryDTO;
import com.nashtech.rootkies.exception.ConvertEntityDTOException;
import com.nashtech.rootkies.model.Category;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    @Autowired
    ModelMapper modelMapper;

    public CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> toDTOList(List<Category> listEntity) throws ConvertEntityDTOException {
        List<CategoryDTO> listDTO;

        try {
            listDTO = listEntity.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ConvertEntityDTOException(ErrorCode.ERR_CONVERT_DTO_ENTITY_FAIL);
        }

        return listDTO;
    }

    public Category convertToEntity(CreateCategoryDTO categoryDTO) {return modelMapper.map(categoryDTO, Category.class);}

}
