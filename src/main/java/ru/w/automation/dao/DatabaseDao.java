package ru.w.automation.dao;

import org.apache.ibatis.annotations.Select;
import ru.w.automation.domain.ColumnDescription;

import java.util.List;

public interface DatabaseDao {

    @Select(
            "SELECT exists(SELECT FROM information_schema.schemata WHERE schema_name = #{schemaName})"
    )
    boolean schemaExists(String schemaName);


    @Select(
            "SELECT exists(SELECT FROM information_schema.tables WHERE table_schema = #{schemaName} AND table_name = #{tableName})"
    )
    boolean tableExists(String schemaName, String tableName);


    @Select(
            "SELECT column_name as columnName, data_type as dataType, character_maximum_length as charMaxLen" +
                    " FROM information_schema.columns WHERE table_schema = #{schemaName} AND table_name = #{tableName}"
    )
    List<ColumnDescription> tableDesc(String schemaName, String tableName);
}