package ru.w.automation.delegate.handling;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.dao.DatabaseDao;
import ru.w.automation.domain.Column;
import ru.w.automation.domain.ConfigurationUpdateRequest;
import ru.w.automation.domain.ProcessVariables;

import java.util.Set;

@Component("getColumnsMappingDelegate")
public class GetColumnsMappingDelegate implements JavaDelegate {

    @Autowired
    private DatabaseDao dao;

    @Override
    public void execute(DelegateExecution execution) {
        ConfigurationUpdateRequest request = new ProcessVariables(execution).getRequest();
        Set<Column> columnDescriptionList = dao.tableDescription(request.getSchemaName(), request.getTableName());

        request.getColumns().addAll(columnDescriptionList);
    }
}
