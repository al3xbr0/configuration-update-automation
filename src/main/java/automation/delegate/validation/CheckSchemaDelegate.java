package automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import automation.dao.DatabaseDao;
import automation.domain.ProcessVariables;

@Component("checkSchemaDelegate")
public class CheckSchemaDelegate implements JavaDelegate {

    @Autowired
    private DatabaseDao dao;

    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);

        variables.getValidationStatus().setSchemaValid(
                dao.schemaExists(variables.getRequest().getSchemaName())
        );
    }
}
