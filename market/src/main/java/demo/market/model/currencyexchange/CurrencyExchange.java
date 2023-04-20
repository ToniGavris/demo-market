package demo.market.model.currencyexchange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyExchange {

	private Header header;
	
	private DataSet[] dataSets;
	
	private Structure structure;
}
