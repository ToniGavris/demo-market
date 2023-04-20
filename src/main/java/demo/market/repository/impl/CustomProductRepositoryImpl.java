package demo.market.repository.impl;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.market.model.Product;
import demo.market.repository.CustomProductRepository;
import demo.market.utils.ProductFilterParams;
import demo.market.utils.ProductUtils;

@Repository
@Transactional(readOnly = true)
public class CustomProductRepositoryImpl implements CustomProductRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findAllProducts(ProductFilterParams filterParams) {
		StringJoiner sql = new StringJoiner(" ", " ", " ");
		// build sql query based on filters
		sql = ProductUtils.addQueryFilters(filterParams);

		// create native query
		Query query = entityManager.createNativeQuery(sql.toString(), Product.class);
		return getPaginatedResults(query.getResultList(), filterParams);
	}

	// method to implement pagination
	private List<Product> getPaginatedResults(List<Product> products, ProductFilterParams filterParams) {
		Integer skip = filterParams.getSkip() == null ? 0 : filterParams.getSkip();
		Integer top = filterParams.getTop() == null ? products.size() : filterParams.getTop();
		return products.stream().skip(skip).limit(top).collect(Collectors.toList());
	}

}
