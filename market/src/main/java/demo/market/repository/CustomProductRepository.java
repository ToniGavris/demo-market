package demo.market.repository;

import java.util.List;

import demo.market.model.Product;
import demo.market.utils.ProductFilterParams;

public interface CustomProductRepository {

	List<Product> findAllProducts(ProductFilterParams filterParams);
}
