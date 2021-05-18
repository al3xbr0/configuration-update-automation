package automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import automation.dao.DatabaseDao;
import automation.domain.Column;
import automation.domain.ConfigurationUpdateRequest;
import automation.domain.ProcessVariables;
import automation.domain.ValidationStatus;

import java.util.Collection;
import java.util.Set;

@Component("checkColumnsMappingDelegate")
public class CheckColumnsMappingDelegate implements JavaDelegate {

    @Autowired
    private DatabaseDao dao;

    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        ConfigurationUpdateRequest request = variables.getRequest();
        ValidationStatus validationStatus = variables.getValidationStatus();
        Collection<Column> columns = request.getColumns();

        Set<Column> columnDescriptionList = dao.tableDescription(request.getSchemaName(), request.getTableName());
        for (Column column : columns) {
            if (
                    !columnDescriptionList.contains(column)
                            && column.isVarchar()
                            && !columnDescriptionList.contains(column.toCharacterVarying())
            ) {
                validationStatus.addInvalidColumn(column);
            }
        }
    }
}
