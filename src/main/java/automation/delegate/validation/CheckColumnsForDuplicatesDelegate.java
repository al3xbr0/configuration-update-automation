package automation.delegate.validation;

import automation.domain.Column;
import automation.domain.ProcessVariables;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component("checkColumnsForDuplicatesDelegate")
public class CheckColumnsForDuplicatesDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        Collection<Column> columns = variables.getRequest().getColumns();
        Set<Column> uniqueColumns = new HashSet<>();

        Set<Column> duplicates = columns.stream()
                .filter(c -> !uniqueColumns.add(c))
                .collect(Collectors.toSet());
        variables.getValidationStatus().addColumnsDuplicates(duplicates);
        if (!duplicates.isEmpty()) {
            variables.setUniqueColumns(uniqueColumns);
        }
    }
}
