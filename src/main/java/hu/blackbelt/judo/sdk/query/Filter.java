package hu.blackbelt.judo.sdk.query;

import java.text.MessageFormat;

public interface Filter {

    String THIS_NAME = "this";

    Operation getOperation();

    String getValueAsString();

    default String toString(String expression) {
        return MessageFormat.format(getOperation().getPattern(), expression, getValueAsString());
    }
}
