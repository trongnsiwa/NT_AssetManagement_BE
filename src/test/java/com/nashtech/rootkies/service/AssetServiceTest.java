//package com.nashtech.rootkies.service;
//
//import com.nashtech.rootkies.converter.AssetConverter;
//import com.nashtech.rootkies.dto.asset.ViewAssetDTO;
//import com.nashtech.rootkies.enums.EAssetState;
//import com.nashtech.rootkies.exception.ConvertEntityDTOException;
//import com.nashtech.rootkies.exception.DataNotFoundException;
//import com.nashtech.rootkies.model.Asset;
//import com.nashtech.rootkies.model.AssetState;
//import com.nashtech.rootkies.model.Category;
//import com.nashtech.rootkies.model.Location;
//import com.nashtech.rootkies.repository.AssetRepository;
//import com.nashtech.rootkies.repository.LocationRepository;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AssetServiceTest {
//    @Autowired
//    AssetService assetService;
//
//    @MockBean
//    AssetRepository assetRepository;
//
//    @MockBean
//    AssetConverter assetConverter;
//
//    @MockBean
//    LocationRepository locationRepository;
//
//    @Test
//    public void testViewListAsset_ReturnShouldBeListAsset() throws ConvertEntityDTOException, DataNotFoundException {
//
////        Location location = new Location();
////        location.setId(1L);
////        location.setName("HCM");
////
////        Category category = new Category();
////        category.setId(1L);
////        category.setName("Laptop");
////        category.setPrefix("LA");
////
////        AssetState state = new AssetState();
////        state.setId(1L);
////        state.setName(EAssetState.AVAILABLE);
////
////        Asset asset = new Asset();
////        asset.setId(1L);
////        asset.setCode("LA001");
////        asset.setName("Laptop 1");
////        asset.setCategory(category);
////        asset.setState(state);
////        asset.setLocation(location);
////
////        Asset asset1 = new Asset();
////        asset1.setId(2L);
////        asset1.setCode("LA002");
////        asset1.setName("Laptop 2");
////        asset1.setCategory(category);
////        asset1.setState(state);
////        asset1.setLocation(location);
////
////        Asset asset2 = new Asset();
////        asset2.setId(3L);
////        asset2.setCode("LA003");
////        asset2.setName("Laptop 3");
////        asset2.setCategory(category);
////        asset2.setState(state);
////        asset2.setLocation(location);
////
////        List<Asset> listAsset = new ArrayList<>();
////        listAsset.add(asset);
////        listAsset.add(asset1);
////        listAsset.add(asset2);
////
////        List<ViewAssetDTO> viewAssetDTOList = assetConverter.toDTOList(listAsset);
////        Page<Asset> paging = null;
////
////        int pageNo = 1;
////        int pageSize = 3;
////        String valueSort = "name";
////
////        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(valueSort));
////
////        when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));
////
////        when(assetRepository.findAll(pageable)).thenReturn(paging);
////
////        when(assetService.getListAllAssetForAdmin(pageNo, pageSize, valueSort, location.getId())).thenReturn(viewAssetDTOList);
////
////        //List<ViewAssetDTO> viewAssetDTOListAct = assetService.getListAllAssetForAdmin(pageNo, pageSize, valueSort, location.getId());
//
//    }
//}
