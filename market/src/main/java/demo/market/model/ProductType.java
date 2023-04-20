package demo.market.model;


import lombok.Getter;

@Getter
public enum ProductType{

	ITALIAN_PASTA("Italian pasta"),
	
	GALUSTE_BANATENE("Galuste banatene"),
	
	SPATZLE("Spatzle");
	
	private String code;
	
	private ProductType(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	
}
