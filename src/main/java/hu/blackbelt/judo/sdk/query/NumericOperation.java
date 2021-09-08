package hu.blackbelt.judo.sdk.query;

public enum NumericOperation implements Operation {

    LESS_THAN("<"),
    LESS_OR_EQUAL_THAN("<="),
    GREATER_THAN(">"),
    GREATER_OR_EQUAL_THAN(">="),
    EQUAL_TO("=="),
    NOT_EQUAL_TO("!=");

    private String operator;

    NumericOperation(String operator) {
        this.operator = operator;
    }

    @Override
    public String getPattern() {
        return "({0} " + operator + " {1})";
    }
}
