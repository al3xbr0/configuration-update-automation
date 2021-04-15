package ru.w.automation.domain;

import org.camunda.bpm.engine.delegate.VariableScope;

import java.util.Collection;

import static ru.w.automation.domain.ProcessConstants.*;

public class ProcessVariables {

    private final VariableScope variableScope;

    public ProcessVariables(VariableScope variableScope) {
        this.variableScope = variableScope;
    }

    public String getIssueKey() {
        return (String) variableScope.getVariable(VAR_NAME_ISSUE_KEY);
    }

    public ConfigurationUpdateRequest getRequest() {
        return (ConfigurationUpdateRequest) variableScope.getVariable(VAR_NAME_REQUEST);
    }

    public void setRequest(ConfigurationUpdateRequest request) {
        variableScope.setVariable(VAR_NAME_REQUEST, request);
    }

    public ValidationStatus getValidationStatus() {
        return (ValidationStatus) variableScope.getVariable(VAR_NAME_VALIDATION_STATUS);
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        variableScope.setVariable(VAR_NAME_VALIDATION_STATUS, validationStatus);
    }

    @SuppressWarnings("unchecked")
    public Collection<Column> getUniqueColumns() {
        return (Collection<Column>) variableScope.getVariable(VAR_NAME_UNIQUE_COLUMNS);
    }

    public void setUniqueColumns(Collection<Column> uniqueColumns) {
        variableScope.setVariableLocal(VAR_NAME_UNIQUE_COLUMNS, uniqueColumns);
    }

    public String getJsonConfig() {
        return (String) variableScope.getVariable(VAR_NAME_JSON_CONFIG);
    }

    public String getJsonConfigName() {
        return (String) variableScope.getVariable(VAR_NAME_JSON_CONFIG_NAME);
    }

    public void setJsonConfigName(String jsonConfigName) {
        variableScope.setVariable(VAR_NAME_JSON_CONFIG_NAME, jsonConfigName);
    }

    public String getTempBranchName() {
        return (String) variableScope.getVariable(VAR_NAME_TEMP_BRANCH_NAME);
    }

    public void setTempBranchName(String tempBranchName) {
        variableScope.setVariable(VAR_NAME_TEMP_BRANCH_NAME, tempBranchName);
    }

    public int getMergeRequestIid() {
        return (int) variableScope.getVariable(VAR_NAME_MERGE_REQUEST_IID);
    }

    public void setMergeRequestIid(int mergeRequestIid) {
        variableScope.setVariable(VAR_NAME_MERGE_REQUEST_IID, mergeRequestIid);
    }

    public void setMergedIntoMaster(boolean mergedIntoMaster) {
        variableScope.setVariable(VAR_NAME_MERGED, mergedIntoMaster);
    }
}
