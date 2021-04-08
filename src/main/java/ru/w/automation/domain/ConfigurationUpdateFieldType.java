package ru.w.automation.domain;

public enum ConfigurationUpdateFieldType {
    SCHEME("Scheme name"),
    TABLE("Table name"),
    COLUMNS("Set of columns"),
    FREQUENCY("Frequency"),
    CREATE_SNAPSHOTS("Extraction type"),

    UNWANTED_FIELD("");

    private final String fieldTypeName;

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public static ConfigurationUpdateFieldType fromJiraName(String name) {
        for (ConfigurationUpdateFieldType t : ConfigurationUpdateFieldType.values()) {
            if (t.fieldTypeName.equalsIgnoreCase(name)) {
                return t;
            }
        }
        return UNWANTED_FIELD;
        //throw new IllegalArgumentException("No Field Type with name " + name);
    }

    ConfigurationUpdateFieldType(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }
}

