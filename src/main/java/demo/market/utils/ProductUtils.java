package demo.market.utils;

import java.time.LocalDate;
import java.util.StringJoiner;

import org.springframework.web.client.RestTemplate;

import demo.market.model.Currency;
import demo.market.model.currencyexchange.CurrencyExchange;
import demo.market.model.currencyexchange.DataSet;
import demo.market.model.currencyexchange.Exchange;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductUtils {

	private final String TABLE_NAME = "products";

	private final String TABLE_ALIAS = "p";

	public static StringJoiner addQueryFilters(ProductFilterParams filters) {
		StringJoiner sql = new StringJoiner(" ", " ", " ");
		sql.add(" SELECT * FROM " + TABLE_NAME + " " + TABLE_ALIAS);
		if (null != filters.getType()) {
			// filter by type
			sql.add("WHERE lower (" + TABLE_ALIAS + ".PRODUCT_TYPE) IN ( '" + filters.getType().replaceAll(",", "','")
					+ "')");
		}
		// sort by a specific column
		sql.add("ORDER BY " + TABLE_ALIAS + "." + (null != filters.getOrderBy() ? filters.getOrderBy() : "created_at"))
				.add((null != filters.getSortingOrderBy() ? filters.getSortingOrderBy() : "desc"));
		return sql;
	}

	// get the exchange rates for current day
	public static Exchange returnExchangeRate() {
		String uri = "https://sdw-wsrest.ecb.europa.eu/service/data/EXR/D.USD+RON.EUR.SP00.A?"
				+ "dimensionAtObservation=AllDimensions" + "&detail=dataonly" + "&format=jsondata" + "&startPeriod="
				+ LocalDate.now().minusDays(1) + "&endPeriod=" + LocalDate.now();
		RestTemplate restTemplate = new RestTemplate();
		CurrencyExchange currencyExchangeResponse = restTemplate.getForObject(uri, CurrencyExchange.class);

		boolean firstEntry = true;
		Exchange exchange = new Exchange();
		// parse third party response
		for (DataSet dataSet : currencyExchangeResponse.getDataSets()) {
			for (Double[] entry : dataSet.getObservations().values()) {
				if (firstEntry) {
					exchange.setForRon(entry[0]);
					firstEntry = false;
				} else {
					exchange.setForUsd(entry[0]);
				}
			}
		}
		return exchange;
	}

	// method to determine the prices after applying the exchange rate for the
	// specified currency
	public static double determineValue(Exchange exchange, Currency from, Currency to, double value) {
		if (!from.name().equals(to.name())) {
			if (from.name().equalsIgnoreCase(Currency.EUR.name()) && to.name().equalsIgnoreCase(Currency.USD.name())) {
				return value * exchange.getForUsd();
			} else if (from.name().equalsIgnoreCase(Currency.EUR.name())
					&& to.name().equalsIgnoreCase(Currency.RON.name())) {
				return value * exchange.getForRon();
			} else if (from.name().equalsIgnoreCase(Currency.USD.name())
					&& to.name().equalsIgnoreCase(Currency.EUR.name())) {
				return value / exchange.getForUsd();
			} else if (from.name().equalsIgnoreCase(Currency.RON.name())
					&& to.name().equalsIgnoreCase(Currency.EUR.name())) {
				return value / exchange.getForRon();
			} else if (from.name().equalsIgnoreCase(Currency.RON.name())
					&& to.name().equalsIgnoreCase(Currency.USD.name())) {
				return value / (exchange.getForRon() / exchange.getForUsd());
			} else {
				return value * (exchange.getForRon() / exchange.getForUsd());
			}
		}
		return value;
	}

}
