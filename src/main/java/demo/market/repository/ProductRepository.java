package demo.market.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import demo.market.model.Product;

public interface ProductRepository extends CrudRepository<Product,Long>{

	Optional<Product> findById(Long id);
}
