package ru.w.automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.ValidationStatus;

@Component("checkSchemaDelegate")
public class CheckSchemaDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        //TODO
        ((ValidationStatus) execution.getVariable("validationStatus")).setSchemaValid(true);

    }
}
