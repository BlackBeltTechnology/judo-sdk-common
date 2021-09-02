package hu.blackbelt.judo.sdk.query;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TimestampFilter implements Filter {

    private NumericOperation operation;
    private OffsetDateTime value;

    private TimestampFilter(NumericOperation operation, OffsetDateTime value) {
        this.operation = operation;
        this.value = value;
    }

    public static TimestampFilter lessThan(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.LESS_THAN, value);
    }

    public static TimestampFilter lessOrEqualThan(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.LESS_OR_EQUAL_THAN, value);
    }

    public static TimestampFilter greaterOrEqualThan(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.GREATER_OR_EQUAL_THAN, value);
    }

    public static TimestampFilter greaterThan(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.GREATER_THAN, value);
    }

    public static TimestampFilter equalTo(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.EQUAL_TO, value);
    }

    public static TimestampFilter notEqualTo(OffsetDateTime value) {
        return new TimestampFilter(NumericOperation.NOT_EQUAL_TO, value);
    }

    @Override
    public NumericOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return "`" + value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "`";
    }
}
