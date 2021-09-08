package hu.blackbelt.judo.sdk.query;

public enum StringOperation implements Operation {
    LESS_THAN("<", false),
    LESS_OR_EQUAL_THAN("<=", false),
    GREATER_THAN(">", false),
    GREATER_OR_EQUAL_THAN(">=", false),
    EQUAL_TO("==", false),
    NOT_EQUAL_TO("!=", false),
    MATCHES("matches", true),
    LIKE("like", true),
    ILIKE("ilike", true);

    private String operator;
    private boolean function;

    StringOperation(String operator, boolean function) {
        this.operator = operator;
        this.function = function;
    }

    @Override
    public String getPattern() {
        if (function) {
            return "({0})!" + operator + "({1})";
        } else {
            return "({0} " + operator + " {1})";
        }
    }
}