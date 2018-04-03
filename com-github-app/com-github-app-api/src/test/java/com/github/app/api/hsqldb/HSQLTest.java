package com.github.app.api.hsqldb;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HSQLTest {

    @Test
    public void test() throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
        c.createStatement().execute("CREATE TABLE `role` (                                                   \n" +
                "          `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT,                   \n" +
                "          `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,                     \n" +
                "          PRIMARY KEY (`role_id`)                                               \n" +
                "        )");
    }
}
