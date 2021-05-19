package automation.dao;

import automation.domain.Column;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

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
        assertTrue(dao.tableExists("public", "test_users"));
        assertFalse(dao.tableExists("public", "no_such_test_table"));
    }

    @Test
    void tableDescription() {
        Set<Column> expected = new HashSet<>();
        expected.add(new Column("id", "integer", null));
        expected.add(new Column("first_name", "character varying", 32));
        expected.add(new Column("last_name", "character varying", 32));
        expected.add(new Column("address", "character varying", 64));
        Set<Column> actual = dao.tableDescription("public", "test_users");
        assertEquals(expected, actual);
    }
}