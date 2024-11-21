package kr.lililli.catalog_service.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import kr.lililli.catalog_service.dto.CatalogDto;
import kr.lililli.catalog_service.jpa.CatalogEntity;
import kr.lililli.catalog_service.jpa.CatalogRepository;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;
    private final ModelMapper modelMapper;

    public CatalogServiceImpl(CatalogRepository catalogRepository, ModelMapper modelMapper) {
        this.catalogRepository = catalogRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CatalogDto> getCatalogByAll() {
        Iterable<CatalogEntity> userEntities = catalogRepository.findAll();
        List<CatalogDto> returnCatalogDtoList = new ArrayList<>();

        userEntities.forEach(v -> {
            returnCatalogDtoList.add(modelMapper.map(v, CatalogDto.class));
        });

        return returnCatalogDtoList;
    }

    @Override
    public CatalogDto getCatalogByProductId(String productId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CatalogDto saveCatalog(CatalogDto catalogDto) {
        // TODO Auto-generated method stub
        return null;
    }

}
