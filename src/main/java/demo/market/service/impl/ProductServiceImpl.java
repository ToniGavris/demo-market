package demo.market.service.impl;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.market.model.Currency;
import demo.market.model.Product;
import demo.market.model.ProductType;
import demo.market.model.currencyexchange.Exchange;
import demo.market.repository.CustomProductRepository;
import demo.market.repository.ProductRepository;
import demo.market.request.ProductRequest;
import demo.market.service.ProductService;
import demo.market.utils.ProductFilterParams;
import demo.market.utils.ProductUtils;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepo;
	private CustomProductRepository customProductRepo;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepo, CustomProductRepository customProductRepo) {
		this.productRepo = productRepo;
		this.customProductRepo = customProductRepo;
	}

	@Override
	public ProductRequest createProduct(ProductRequest request) {
		return prepareResponse(null, productRepo.save(getProductFromRequest(request)));
	}

	@Override
	public ProductRequest editProduct(Long id, ProductRequest request) {
		Product product = new Product();
		try {
			// find product by id
			product = productRepo.findById(id).orElseThrow(NotFoundException::new);

			// update product properties
			product.setName(request.getName());
			product.setDescription(request.getDescription());
			product.setValue(request.getValue());
			product.setCurrency(
					Stream.of(Currency.values()).filter(f -> f.getCode().equalsIgnoreCase(request.getCurrency()))
							.findFirst().orElseThrow(NotFoundException::new));
			product.setItemsInStock(request.getItemsInStock());
			product.setUpdateDate(Timestamp.from(ZonedDateTime.now().toInstant()));
		} catch (NotFoundException e) {
			// throw new CustomException
			// need to implement a custom exception where we'll give an explicit message
		}
		return prepareResponse(null, productRepo.save(product));
	}

	@Override
	public void retireProduct(Long id, String reason) {
		try {
			Product product = productRepo.findById(id).orElseThrow(NotFoundException::new);
			// check if product is available
			if (!product.isAvailable()) {
				// throw a meaningful exception
			}
			// update availability and reason of retirement
			product.setAvailable(false);
			product.setReasonForRetirement(reason);
			productRepo.save(product);
		} catch (Exception e) {
			// meaningful message
		}
	}

	@Override
	public List<ProductRequest> getProducts(ProductFilterParams filterParams) {
		// find all products
		List<Product> products = (List<Product>) customProductRepo.findAllProducts(filterParams);

		// get the exchange rate from current day for ron->eur and usd->eur
		Exchange exchange = ProductUtils.returnExchangeRate();

		// add exchange rates to response
		return products.stream().map(f -> prepareResponse(exchange, f)).collect(Collectors.toList());
	}

	@Override
	public ProductRequest buyProduct(Long id, Integer quantity) {
		try {
			Product product = productRepo.findById(id).orElseThrow(NotFoundException::new);
			int availableQuantity = product.getItemsInStock();
			if (product.isAvailable() && availableQuantity >= quantity) {
				product.setItemsInStock(availableQuantity - quantity);
				productRepo.save(product);
				return prepareResponse(null, productRepo.save(product));
			} else {
				// product is not available
			}

		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// method to map product from request body
	private Product getProductFromRequest(ProductRequest request) {
		return Product.builder().name(request.getName()).description(request.getDescription())
				.type(Stream.of(ProductType.values()).filter(f -> f.getCode().equalsIgnoreCase(request.getType()))
						.findFirst().orElse(null))
				.currency(Stream.of(Currency.values()).filter(f -> f.getCode().equalsIgnoreCase(request.getCurrency()))
						.findFirst().orElse(null))
				.value(request.getValue()).createDate(Timestamp.from(ZonedDateTime.now().toInstant()))
				.updateDate(Timestamp.from(ZonedDateTime.now().toInstant())).itemsInStock(request.getItemsInStock())
				.available(request.isAvailable()).build();
	}

	// custom method to return product response
	private ProductRequest prepareResponse(Exchange exchange, Product p) {
		return ProductRequest.builder().id(p.getId()).name(p.getName()).description(p.getDescription())
				.type(p.getType().getCode()).currency(p.getCurrency().getCode()).prices(determinePrices(exchange, p))
				.value(p.getValue()).itemsInStock(p.getItemsInStock()).available(p.isAvailable()).build();
	}

	// method to determine the prices for all currencies available
	private EnumMap<Currency, Double> determinePrices(Exchange exchange, Product p) {
		EnumMap<Currency, Double> prices = new EnumMap<>(Currency.class);
		if (null != exchange) {
			for (Currency currency : Currency.values()) {
				prices.put(currency, ProductUtils.determineValue(exchange, p.getCurrency(), currency, p.getValue()));
			}
		}
		return prices;
	}

}
