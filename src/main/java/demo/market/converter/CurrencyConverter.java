package demo.market.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import demo.market.model.Currency;

@Converter(autoApply=true)
public class CurrencyConverter extends GenericConverter<Currency> implements AttributeConverter<Currency,String>{

	protected CurrencyConverter() {
		super(Currency.class);
	}

}
