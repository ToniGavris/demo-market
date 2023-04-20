package demo.market.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import demo.market.model.ProductType;


@Converter(autoApply=true)
public class ProductTypeConverter extends GenericConverter<ProductType> implements AttributeConverter<ProductType,String>{

	public ProductTypeConverter() {
		super(ProductType.class);
	}
	
}
