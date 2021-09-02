package hu.blackbelt.judo.sdk.query;

public class EnumerationFilter<E> implements Filter {

    private EnumerationOperation operation;
    private E value;

    private EnumerationFilter(EnumerationOperation operation, E value) {
        this.operation = operation;
        this.value = value;
    }

    public static <E> EnumerationFilter equalTo(E value) {
        return new EnumerationFilter(EnumerationOperation.EQUAL_TO, value);
    }

    public static <E> EnumerationFilter notEqualTo(E value) {
        return new EnumerationFilter(EnumerationOperation.NOT_EQUAL_TO, value);
    }

    @Override
    public EnumerationOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        // FIXME - return enumeration literal as JQL string
        return value.toString();
    }
}
