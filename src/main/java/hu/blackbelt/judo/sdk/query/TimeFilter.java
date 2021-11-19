package hu.blackbelt.judo.sdk.query;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFilter implements Filter {

    private NumericOperation operation;
    private LocalTime value;

    private TimeFilter(NumericOperation operation, LocalTime value) {
        this.operation = operation;
        this.value = value;
    }

    public static TimeFilter lessThan(LocalTime value) {
        return new TimeFilter(NumericOperation.LESS_THAN, value);
    }

    public static TimeFilter lessOrEqualThan(LocalTime value) {
        return new TimeFilter(NumericOperation.LESS_OR_EQUAL_THAN, value);
    }

    public static TimeFilter greaterOrEqualThan(LocalTime value) {
        return new TimeFilter(NumericOperation.GREATER_OR_EQUAL_THAN, value);
    }

    public static TimeFilter greaterThan(LocalTime value) {
        return new TimeFilter(NumericOperation.GREATER_THAN, value);
    }

    public static TimeFilter equalTo(LocalTime value) {
        return new TimeFilter(NumericOperation.EQUAL_TO, value);
    }

    public static TimeFilter notEqualTo(LocalTime value) {
        return new TimeFilter(NumericOperation.NOT_EQUAL_TO, value);
    }

    @Override
    public NumericOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return "`" + value.format(DateTimeFormatter.ISO_LOCAL_TIME) + "`";
    }
}
