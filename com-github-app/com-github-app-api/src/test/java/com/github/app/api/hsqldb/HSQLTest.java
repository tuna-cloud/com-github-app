package com.github.app.api.hsqldb;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HSQLTest {

    @Test
    public void test() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:hsqldb:file:E:\\db\\testdb", "SA", "");
        c.createStatement().execute("CREATE TABLE t(a INTEGER, b BIGINT)");
    }
}
