package hu.blackbelt.judo.sdk.query;

import hu.blackbelt.judo.sdk.Enumeration;

public class EnumerationFilter implements Filter {

    private EnumerationOperation operation;
    private Enumeration value;

    private EnumerationFilter(EnumerationOperation operation, Enumeration value) {
        this.operation = operation;
        this.value = value;
    }

    public static EnumerationFilter equalTo(Enumeration value) {
        return new EnumerationFilter(EnumerationOperation.EQUAL_TO, value);
    }

    public static EnumerationFilter notEqualTo(Enumeration value) {
        return new EnumerationFilter(EnumerationOperation.NOT_EQUAL_TO, value);
    }

    @Override
    public EnumerationOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return value.getFqName();
    }
}
