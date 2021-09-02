package hu.blackbelt.judo.sdk.query;

public class NumberFilter implements Filter {

    private NumericOperation operation;
    private Number value;

    private NumberFilter(NumericOperation operation, Number value) {
        this.operation = operation;
        this.value = value;
    }

    public static NumberFilter lessThan(Number value) {
        return new NumberFilter(NumericOperation.LESS_THAN, value);
    }

    public static NumberFilter lessOrEqualThan(Number value) {
        return new NumberFilter(NumericOperation.LESS_OR_EQUAL_THAN, value);
    }

    public static NumberFilter greaterOrEqualThan(Number value) {
        return new NumberFilter(NumericOperation.GREATER_OR_EQUAL_THAN, value);
    }

    public static NumberFilter greaterThan(Number value) {
        return new NumberFilter(NumericOperation.GREATER_THAN, value);
    }

    public static NumberFilter equalTo(Number value) {
        return new NumberFilter(NumericOperation.EQUAL_TO, value);
    }

    public static NumberFilter notEqualTo(Number value) {
        return new NumberFilter(NumericOperation.NOT_EQUAL_TO, value);
    }

    @Override
    public NumericOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }
}
