package ru.w.automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import ru.w.automation.domain.ValidationStatus;

public class CheckTableDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //TODO
        ((ValidationStatus) execution.getVariable("validationStatus")).setTableValid(true);
    }
}
