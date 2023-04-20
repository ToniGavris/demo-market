package demo.market.model.currencyexchange;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSet {

	private String action;

	private Date validFrom;

	private Map<String, Double[]> observations;
}
