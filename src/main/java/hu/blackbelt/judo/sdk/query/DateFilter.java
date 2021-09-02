package hu.blackbelt.judo.sdk.query;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFilter implements Filter {

    private NumericOperation operation;
    private LocalDate value;

    private DateFilter(NumericOperation operation, LocalDate value) {
        this.operation = operation;
        this.value = value;
    }

    public static DateFilter lessThan(LocalDate value) {
        return new DateFilter(NumericOperation.LESS_THAN, value);
    }

    public static DateFilter lessOrEqualThan(LocalDate value) {
        return new DateFilter(NumericOperation.LESS_OR_EQUAL_THAN, value);
    }

    public static DateFilter greaterOrEqualThan(LocalDate value) {
        return new DateFilter(NumericOperation.GREATER_OR_EQUAL_THAN, value);
    }

    public static DateFilter greaterThan(LocalDate value) {
        return new DateFilter(NumericOperation.GREATER_THAN, value);
    }

    public static DateFilter equalTo(LocalDate value) {
        return new DateFilter(NumericOperation.EQUAL_TO, value);
    }

    public static DateFilter notEqualTo(LocalDate value) {
        return new DateFilter(NumericOperation.NOT_EQUAL_TO, value);
    }

    @Override
    public NumericOperation getOperation() {
        return operation;
    }

    @Override
    public String getValueAsString() {
        return "`" + value.format(DateTimeFormatter.ISO_DATE) + "`";
    }
}
