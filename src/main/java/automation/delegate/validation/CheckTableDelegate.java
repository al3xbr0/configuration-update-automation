package automation.delegate.validation;

import automation.dao.DatabaseDao;
import automation.domain.ConfigurationUpdateRequest;
import automation.domain.ProcessVariables;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("checkTableDelegate")
public class CheckTableDelegate implements JavaDelegate {

    @Autowired
    private DatabaseDao dao;

    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        ConfigurationUpdateRequest request = variables.getRequest();

        variables.getValidationStatus().setTableValid(
                dao.tableExists(request.getSchemaName(), request.getTableName())
        );
    }
}
