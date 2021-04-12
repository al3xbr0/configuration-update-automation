package ru.w.automation.domain;

public class ValidationStatus {
    private boolean schemaValid;
    private boolean tableValid;
    private boolean columnDuplicates;
    private boolean columnsExist;

    private String validationComment;

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

    public boolean isColumnDuplicates() {
        return columnDuplicates;
    }

    public void setColumnDuplicates(boolean columnDuplicates) {
        this.columnDuplicates = columnDuplicates;
    }

    public boolean isColumnsExist() {
        return columnsExist;
    }

    public void setColumnsExist(boolean columnsExist) {
        this.columnsExist = columnsExist;
    }

    public String getValidationComment() {
        return validationComment;
    }

    public void setValidationComment(String validationComment) {
        this.validationComment = validationComment;
    }

    public boolean validationSucceed() {
        return schemaValid && tableValid && columnDuplicates && columnsExist;
    }
}
