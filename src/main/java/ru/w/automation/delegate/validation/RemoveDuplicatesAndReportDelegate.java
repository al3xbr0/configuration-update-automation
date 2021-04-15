package ru.w.automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.Column;
import ru.w.automation.domain.ProcessVariables;
import ru.w.automation.domain.ValidationStatus;

import java.util.Collection;

@Component("removeDuplicatesAndReportDelegate")
public class RemoveDuplicatesAndReportDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        Collection<Column> columns = variables.getRequest().getColumns();
        ValidationStatus validationStatus = variables.getValidationStatus();

        columns.clear();
        columns.addAll(variables.getUniqueColumns());

        validationStatus.appendValidationComment(
                "Found duplicates in \"Set of columns\" field: " + validationStatus.getPrintableColumnsDuplicates() +
                        ". They have been removed from the request."
        );
    }
}
