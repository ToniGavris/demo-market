package demo.market.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterParams {

	String status;

	String type;

	String orderBy;

	String sortingOrderBy;

	Integer top;

	Integer skip;
}
