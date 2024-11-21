package kr.lililli.catalog_service.controller;

import org.springframework.web.bind.annotation.RestController;
import kr.lililli.catalog_service.dto.CatalogDto;
import kr.lililli.catalog_service.service.CatalogService;
import kr.lililli.catalog_service.vo.ApiResponse;
import kr.lililli.catalog_service.vo.ResponseCatalog;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/catalog-service")
public class CatalogController {


    private final CatalogService catalogService;
    private final ModelMapper modelMapper;

    public CatalogController(CatalogService catalogService, ModelMapper modelMapper) {
        this.catalogService = catalogService;
        this.modelMapper = modelMapper;
    }


    @GetMapping({"", "/"})
    public String getHello() {
        return "Hello. This is Catalog Service";
    }


    @GetMapping("catalogs")
    public ResponseEntity<?> getCatalogByAll() {
        List<CatalogDto> catalogDtoList = catalogService.getCatalogByAll();
        List<ResponseCatalog> responseCatalogList = new ArrayList<>();

        catalogDtoList.forEach(v -> {
            responseCatalogList.add(modelMapper.map(v, ResponseCatalog.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseCatalogList));
    }


}
