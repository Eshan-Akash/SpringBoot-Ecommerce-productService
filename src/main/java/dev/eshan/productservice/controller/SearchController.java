package dev.eshan.productservice.controller;

import dev.eshan.productservice.dtos.GenericProductDto;
import dev.eshan.productservice.dtos.SearchRequestDto;
import dev.eshan.productservice.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    private SearchService searchService;
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping
    public Page<GenericProductDto> searchProducts(@RequestBody SearchRequestDto searchRequestDto) {
        return searchService.searchProducts(searchRequestDto.getQuery(),
                PageRequest.of(searchRequestDto.getPageNumber(), searchRequestDto.getSizeOfEachPage()));
    }
}
