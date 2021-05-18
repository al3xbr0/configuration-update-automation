package automation.delegate.handling;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import automation.dao.DatabaseDao;
import automation.domain.Column;
import automation.domain.ConfigurationUpdateRequest;
import automation.domain.ProcessVariables;

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
