package demo.market.model.currencyexchange;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyObject {

	private String id;
	
	private String name;
	
	private List<Value> values;
}
