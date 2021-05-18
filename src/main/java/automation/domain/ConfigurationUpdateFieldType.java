package automation.domain;

public enum ConfigurationUpdateFieldType {
    SCHEMA("Schema name"),
    TABLE("Table name"),
    COLUMNS("Set of columns"),
    FREQUENCY("Frequency"),
    CREATE_SNAPSHOTS("Create snapshots"),

    UNWANTED_FIELD("");

    private final String fieldTypeName;

    public static ConfigurationUpdateFieldType fromJiraName(String name) {
        for (ConfigurationUpdateFieldType type : ConfigurationUpdateFieldType.values()) {
            if (type.fieldTypeName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return UNWANTED_FIELD;
    }

    ConfigurationUpdateFieldType(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }
}

