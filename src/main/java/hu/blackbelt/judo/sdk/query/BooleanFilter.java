package hu.blackbelt.judo.sdk.query;

public class BooleanFilter implements Filter {

    private BooleanOperation operation;

    private BooleanFilter(BooleanOperation operation) {
        this.operation = operation;
    }

    public static BooleanFilter isTrue() {
        return new BooleanFilter(BooleanOperation.TRUE);
    }

    public static BooleanFilter isFalse() {
        return new BooleanFilter(BooleanOperation.FALSE);
    }

    @Override
    public BooleanOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        // not used by pattern
        return operation.toString();
    }
}