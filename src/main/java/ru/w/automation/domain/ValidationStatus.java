package ru.w.automation.domain;

public class ValidationStatus {
    private boolean schemaValid;
    private boolean tableValid;
    private boolean columnDuplicates;
    private boolean columnsExist;

    private StringBuilder validationComment;

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

    public boolean hasColumnDuplicates() {
        return columnDuplicates;
    }

    public void setColumnDuplicates(boolean columnDuplicates) {
        this.columnDuplicates = columnDuplicates;
    }

    public boolean areColumnsExist() {
        return columnsExist;
    }

    public void setColumnsExist(boolean columnsExist) {
        this.columnsExist = columnsExist;
    }

    public String getValidationComment() {
        return validationComment.toString();
    }

    public void appendComment(String text){
        validationComment.append(text);
    }

    public boolean validationSucceed() {
        return schemaValid && tableValid && columnDuplicates && columnsExist;
    }
}
