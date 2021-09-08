package hu.blackbelt.judo.sdk.query;

public enum BooleanOperation implements Operation {
    TRUE {
        @Override
        public String getPattern() {
            return "{0}";
        }
    },
    FALSE {
        @Override
        public String getPattern() {
            return "(not {0})";
        }
    };
}