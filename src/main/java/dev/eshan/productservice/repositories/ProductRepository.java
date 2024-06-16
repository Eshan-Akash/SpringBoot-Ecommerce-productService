package dev.eshan.productservice.repositories;

import dev.eshan.productservice.model.Category;
import dev.eshan.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, String> {
//    List<Product> getAll(Pageable pageable);

    Product findByTitleEquals(String title);
    Product findByTitleEqualsAndPrice_PriceOrderByPrice_price(String title, double price);
    Product findByTitleEqualsAndPrice_Price(String title, double price);

    List<Product> findDistinctByPrice_Currency(String currency);
    List<Product> findAllByTitleLike(String titleRegex);
    @Query(value = "SELECT * from product where id= :id", nativeQuery = true)
    Product getProduct(String id);

    @Query("SELECT p from Product p where p.id = :id")
    Product readProduct(String id);

    List<Product> findAllByCategoryIn(List<Category> categories);

    Page<Product> findAllByTitleContaining(String title, Pageable pageable);
}
