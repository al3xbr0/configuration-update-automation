package automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import automation.domain.Column;
import automation.domain.ProcessVariables;
import automation.domain.ValidationStatus;

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
