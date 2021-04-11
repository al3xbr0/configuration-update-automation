package ru.w.automation.domain;

public class ValidationStatus {
    private boolean schemeValid;
    private boolean tableValid;
    private boolean columnDuplicates;
    private boolean columnsExist;

    private String validationComment;

    public boolean isSchemeValid() {
        return schemeValid;
    }

    public void setSchemeValid(boolean schemeValid) {
        this.schemeValid = schemeValid;
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
        return schemeValid && tableValid && columnDuplicates && columnsExist;
    }
}
