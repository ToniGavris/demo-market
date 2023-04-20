package demo.market.model.currencyexchange;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Header {

	private String id;
	
	private String test;
	
	private Date prepared;
	
	private Map<String,String> sender;
}
