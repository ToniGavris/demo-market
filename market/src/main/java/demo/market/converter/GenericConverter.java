package demo.market.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;

public abstract class GenericConverter<T extends Enum<T>> implements AttributeConverter<T, String> {

	private final Class<T> clazz;

	protected GenericConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String convertToDatabaseColumn(T attribute) {
		if (null == attribute) {
			return null;
		}
		return attribute.name();
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		if (null == dbData) {
			return null;
		}
		return Stream.of(dbData).map(f -> Enum.valueOf(clazz, f)).filter(f -> f.name().equalsIgnoreCase(dbData))
				.findFirst().orElseThrow(IllegalArgumentException::new);
	}

}
