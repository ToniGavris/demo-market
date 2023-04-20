package demo.market.model.currencyexchange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Structure {

	private String name;
	
	private Link[] links;
	
	private Dimension dimensions;
}
