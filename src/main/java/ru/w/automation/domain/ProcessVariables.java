package ru.w.automation.domain;

import org.camunda.bpm.engine.delegate.VariableScope;

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
}
