package ru.w.automation.domain;

import java.io.Serializable;

public class Column implements Serializable {

    private static final String VARCHAR = "varchar";
    private static final String CHARACTER_VARYING = "character varying";

    private final String name;
    private final String dataType;
    private final Integer charMaxLen;

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType + (charMaxLen != null ? "(" + charMaxLen.toString() + ")" : "");
    }

    public Column(String name, String dataType, Integer charMaxLen) {
        this.name = name;
        this.dataType = dataType;
        this.charMaxLen = charMaxLen;
    }

    public Column(String name, String dataType) {
        this(name, dataType, null);
    }

    public boolean isVarchar() {
        return VARCHAR.equals(dataType);
    }

    public Column toCharacterVarying() {
        if (isVarchar()) {
            return new Column(name, CHARACTER_VARYING, charMaxLen);
        }
        throw new IllegalStateException("Datatype must be " + VARCHAR);
    }

    @Override
    public String toString() {
        return "\"" + name + "\"" + ":" +
                "\"" + dataType + "\"" + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (!name.equals(column.name)) return false;
        return getDataType().equals(column.getDataType());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + getDataType().hashCode();
        return result;
    }
}
