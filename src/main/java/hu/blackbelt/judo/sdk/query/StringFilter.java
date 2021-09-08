package hu.blackbelt.judo.sdk.query;

public class StringFilter implements Filter {

    private StringOperation operation;
    private String value;

    private StringFilter(StringOperation operation, String value) {
        this.operation = operation;
        this.value = value;
    }

    public static StringFilter lessThan(String value) {
        return new StringFilter(StringOperation.LESS_THAN, value);
    }

    public static StringFilter lessOrEqualThan(String value) {
        return new StringFilter(StringOperation.LESS_OR_EQUAL_THAN, value);
    }

    public static StringFilter greaterOrEqualThan(String value) {
        return new StringFilter(StringOperation.GREATER_OR_EQUAL_THAN, value);
    }

    public static StringFilter greaterThan(String value) {
        return new StringFilter(StringOperation.GREATER_THAN, value);
    }

    public static StringFilter equalTo(String value) {
        return new StringFilter(StringOperation.EQUAL_TO, value);
    }

    public static StringFilter notEqualTo(String value) {
        return new StringFilter(StringOperation.NOT_EQUAL_TO, value);
    }

    public static StringFilter matches(String value) {
        return new StringFilter(StringOperation.MATCHES, value);
    }

    public static StringFilter like(String value) {
        return new StringFilter(StringOperation.LIKE, value);
    }

    public static StringFilter ilike(String value) {
        return new StringFilter(StringOperation.ILIKE, value);
    }

    @Override
    public StringOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return '"' + value.replace("\"", "\\\"") + '"';
    }
}