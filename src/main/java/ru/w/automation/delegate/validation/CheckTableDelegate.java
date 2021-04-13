package ru.w.automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.dao.DatabaseDao;
import ru.w.automation.domain.ConfigurationUpdateRequest;
import ru.w.automation.domain.ProcessVariables;

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
