package ru.w.automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CheckSchemeDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //TODO
        execution.setVariable("schemeOk", true);

    }
}
