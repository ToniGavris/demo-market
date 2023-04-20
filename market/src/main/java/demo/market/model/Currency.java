package demo.market.model;

import lombok.Getter;

@Getter
public enum Currency {

	RON("Ron"),
	
	EUR("Euro"),
	
	USD("Dollar");
	
	private String code;
	
	private Currency(String code){ 
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}
	
}
