package demo.market.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	@Column(nullable = false, name = "product_name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "product_type")
	private ProductType type;
	
	@Column(name = "value")
	private double value;
	
	@Column(name = "currency")
	private Currency currency;
	
	@Column(name = "created_at")
	private Timestamp createDate;
	
	@Column(name = "updated_at")
	private Timestamp updateDate;
	
	@Column(name = "in_stock")
	private int itemsInStock;
	
	@Column(name = "available")
	private boolean available;
	
	@Column(name = "reason_for_retirement")
	private String reasonForRetirement;
}
