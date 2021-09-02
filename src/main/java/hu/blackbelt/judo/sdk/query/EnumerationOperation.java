package hu.blackbelt.judo.sdk.query;

public enum EnumerationOperation implements Operation {

    EQUAL_TO("=="),
    NOT_EQUAL_TO("!=");

    private String operator;

    EnumerationOperation(String operator) {
        this.operator = operator;
    }

    @Override
    public String getPattern() {
        return "({0} " + operator + " {1})";
    }
}