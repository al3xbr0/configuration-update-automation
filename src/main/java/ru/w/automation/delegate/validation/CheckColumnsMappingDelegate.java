package ru.w.automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component("checkColumnsMappingDelegate")
public class CheckColumnsMappingDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        //TODO
    }
}
