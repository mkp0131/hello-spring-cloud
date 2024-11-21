package kr.lililli.catalog_service.service;

import java.util.List;
import kr.lililli.catalog_service.dto.CatalogDto;


public interface CatalogService {
    CatalogDto getCatalogByProductId(String productId);

    CatalogDto saveCatalog(CatalogDto catalogDto);

    List<CatalogDto> getCatalogByAll();
}
