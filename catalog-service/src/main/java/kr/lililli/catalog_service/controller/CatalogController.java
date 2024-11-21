package kr.lililli.catalog_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
    @GetMapping({"", "/"})
    public String getHello() {
        return "Hello. This is Catalog Service";
    }

}
