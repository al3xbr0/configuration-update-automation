package ru.w.automation.domain;

public class ColumnDescription {

    private final String columnName;
    private final String dataType;
    private final Integer charMaxLen;

    public String getColumnName() {
        return columnName;
    }

    public String getDataType() {
        return dataType + (charMaxLen != null ? "(" + charMaxLen.toString() + ")" : "");
    }

    public ColumnDescription(String columnName, String dataType, Integer charMaxLen) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.charMaxLen = charMaxLen;
    }
}