package io.leo.ecommerce.dao;

import io.leo.ecommerce.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "productcategory",path = "product-category")
public interface ProductCategoryRepository extends JpaRepository <ProductCategory, Long> {
}
