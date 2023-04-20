package demo.market.request;

import java.util.EnumMap;

import demo.market.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class ProductRequest {

	private final Long id;

	private final String name;

	private final String description;

	private final String type;

	private final String currency;

	private final EnumMap<Currency, Double> prices;

	private final double value;

	private final int itemsInStock;

	private final boolean available;
}
