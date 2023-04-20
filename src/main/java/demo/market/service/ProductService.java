package demo.market.service;

import java.util.List;

import demo.market.request.ProductRequest;
import demo.market.utils.ProductFilterParams;

public interface ProductService {

	ProductRequest createProduct(ProductRequest request);

	ProductRequest editProduct(Long id, ProductRequest request);

	void retireProduct(Long id, String reason);

	List<ProductRequest> getProducts(ProductFilterParams filterParams);

	ProductRequest buyProduct(Long id, Integer quantity);
}
