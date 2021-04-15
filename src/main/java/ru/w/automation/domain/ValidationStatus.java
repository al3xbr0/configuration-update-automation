package ru.w.automation.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ValidationStatus implements Serializable {
    private boolean schemaValid;
    private boolean tableValid;
    private final Set<Column> columnsDuplicates = new HashSet<>();
    private final Set<Column> invalidColumns = new HashSet<>();

    private final StringBuilder validationComment = new StringBuilder("VALIDATION STATUS\n-----------------\n");

    public boolean isSchemaValid() {
        return schemaValid;
    }

    public void setSchemaValid(boolean schemaValid) {
        this.schemaValid = schemaValid;
    }

    public boolean isTableValid() {
        return tableValid;
    }

    public void setTableValid(boolean tableValid) {
        this.tableValid = tableValid;
    }

    public String getPrintableColumnsDuplicates() {
        return columnsDuplicates.stream().map(Column::getName)
                .collect(Collectors.joining("', '", "'", "'"));
    }

    public boolean hasColumnDuplicates() {
        return !columnsDuplicates.isEmpty();
    }

    public void addColumnsDuplicates(Set<Column> columnsDuplicates) {
        this.columnsDuplicates.addAll(columnsDuplicates);
    }

    public String getPrintableInvalidColumns() {
        return invalidColumns.stream().map(
                column -> column.getName() + " : " + column.getPrintableDataType()
        ).collect(Collectors.joining("', '", "'", "'"));
    }

    public void addInvalidColumn(Column column) {
        invalidColumns.add(column);
    }

    public boolean getColumnsExist() {
        return invalidColumns.isEmpty();
    }

    public String getValidationComment() {
        return validationComment.toString();
    }

    public void appendValidationComment(String text) {
        validationComment.append(text).append("\n");
    }

    public boolean isValidationSuccessful() {
        return schemaValid && tableValid && getColumnsExist();
    }
}
