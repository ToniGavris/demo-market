package demo.market.model.currencyexchange;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataSetObservation {

	Map<String,List<Double>> values;
}
