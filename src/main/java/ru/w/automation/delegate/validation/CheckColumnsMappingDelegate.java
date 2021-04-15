package ru.w.automation.delegate.validation;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.dao.DatabaseDao;
import ru.w.automation.domain.Column;
import ru.w.automation.domain.ConfigurationUpdateRequest;
import ru.w.automation.domain.ProcessVariables;
import ru.w.automation.domain.ValidationStatus;

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
