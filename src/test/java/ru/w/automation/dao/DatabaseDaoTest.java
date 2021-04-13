package ru.w.automation.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.w.automation.domain.ColumnDescription;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseDaoTest {

    @Autowired
    private DatabaseDao dao;

    @Test
    void schemaExists() {
        assertTrue(dao.schemaExists("public"));
        assertFalse(dao.schemaExists("random_schema"));
    }

    @Test
    void tableExists() {
        assertTrue(dao.tableExists("public", "test_table_always_exists"));
        assertFalse(dao.tableExists("public", "no_such_test_table"));
    }

    @Test
    void tableDescription() {
        List<ColumnDescription> expected = new ArrayList<>();
        expected.add(new ColumnDescription("test", "integer", null));
        expected.add(new ColumnDescription("test2", "character varying", 32));
        List<ColumnDescription> actual = dao.tableDescription("public", "test_table_always_exists");
        assertEquals(expected, actual);
    }
}