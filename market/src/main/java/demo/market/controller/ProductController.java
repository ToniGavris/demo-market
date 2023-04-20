package demo.market.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.market.request.ProductRequest;
import demo.market.service.ProductService;
import demo.market.utils.ProductFilterParams;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<Object> createProduct(@RequestBody ProductRequest request) {
		return new ResponseEntity<Object>(productService.createProduct(request), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> editProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
		return new ResponseEntity<Object>(productService.editProduct(id, request), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<Object> getProducts(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "sortingOrder", required = false) String sortingOrderBy,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "top", required = false) Integer top,
			@RequestParam(value = "skip", required = false) Integer skip) {

		ProductFilterParams filterParams = new ProductFilterParams();
		filterParams.setTop(top);
		filterParams.setSkip(skip);
		filterParams.setOrderBy(orderBy);
		filterParams.setSortingOrderBy(sortingOrderBy);
		filterParams.setType(type);

		return new ResponseEntity<Object>(productService.getProducts(filterParams), HttpStatus.OK);
	}

	@PutMapping("/{id}/retire")
	public ResponseEntity<Object> retireProduct(@PathVariable Long id,
			@RequestBody(required = false) Map<String, String> requestBody) {
		productService.retireProduct(id, requestBody.get("reason"));
		return new ResponseEntity<Object>(new Object(), HttpStatus.OK);
	}

	@PostMapping("/{id}/buy")
	public ResponseEntity<Object> buyProduct(@PathVariable Long id,
			@RequestBody(required = true) Map<String, Integer> requestBody) {
		return new ResponseEntity<Object>(productService.buyProduct(id, requestBody.get("quantity")), HttpStatus.OK);
	}

	// need to implement an Exception Handler in order to handle all the exceptions
	// that we get and to return the http status code that represent better the
	// cause of the exception

}
