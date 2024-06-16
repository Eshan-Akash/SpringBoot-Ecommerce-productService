package dev.eshan.productservice.repositories;

import dev.eshan.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductElasticSearchRepository extends ElasticsearchRepository<Product, String> {
    Page<Product> findAllByTitleContaining(String title, Pageable pageable);

    List<Product> findAllByTitleContainingOrDescriptionContaining(String query);
}
