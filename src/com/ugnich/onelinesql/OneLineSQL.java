package com.ugnich.onelinesql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Perform queries using java.sql with one line of code.
 * https://github.com/ugnich/OneLineSQL
 * MIT License
 * @version 1.0
 */
public class OneLineSQL {

    /**
     * For SELECT queries that will return single boolean result
     * Example: getBoolean(mysql, "SELECT 1 FROM users WHERE name=? AND age=?", "Alex", 21);
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return 'true' if the first line, first column of results is TRUE, otherwise 'false'
     */
    public static boolean getBoolean(Connection sql, String query, Object... params) {
        boolean ret = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = sql.prepareStatement(query);
            setStatementParams(stmt, params);
            rs = stmt.executeQuery();
            if (rs.first() && rs.getBoolean(1)) {
                ret = true;
            }
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(rs, stmt);
        }
        return ret;
    }

    /**
     * For SELECT queries that will return single integer result
     * Example: getInt(mysql, "SELECT age FROM users WHERE user_id=?", 1234);
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return Integer from the first line, first column of results, otherwise 0
     * @see #getInteger(java.sql.Connection, java.lang.String, java.lang.Object[]) 
     */
    public static int getInt(Connection sql, String query, Object... params) {
        int ret = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = sql.prepareStatement(query);
            setStatementParams(stmt, params);
            rs = stmt.executeQuery();
            if (rs.first()) {
                ret = rs.getInt(1);
            }
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(rs, stmt);
        }
        return ret;
    }

    /**
     * For SELECT queries that will return single integer result
     * Example: getInteger(mysql, "SELECT age FROM users WHERE user_id=?", 1234);
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return Integer from the first line, first column of results, otherwise null
     * @see #getInt(java.sql.Connection, java.lang.String, java.lang.Object[]) 
     */
    public static Integer getInteger(Connection sql, String query, Object... params) {
        Integer ret = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = sql.prepareStatement(query);
            setStatementParams(stmt, params);
            rs = stmt.executeQuery();
            if (rs.first()) {
                ret = new Integer(rs.getInt(1));
            }
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(rs, stmt);
        }
        return ret;
    }

    /**
     * For SELECT queries that will return single Long result Example:
     * getLong(mysql, "SELECT uts FROM users WHERE user_id=?", 1234);
     *
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return Long from the first line, first column of results, otherwise null
     */
    public static Long getLong(Connection sql, String query, Object... params) {
        Long ret = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = sql.prepareStatement(query);
            setStatementParams(stmt, params);
            rs = stmt.executeQuery();
            if (rs.first()) {
                ret = rs.getLong(1);
            }
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(rs, stmt);
        }
        return ret;
    }

    /**
     * For SELECT queries that will return single string result
     * Example: getString(mysql, "SELECT name FROM users WHERE user_id=?", 1234);
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return String from the first line, first column of results, otherwise null
     */
    public static String getString(Connection sql, String query, Object... params) {
        String ret = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = sql.prepareStatement(query);
            setStatementParams(stmt, params);
            rs = stmt.executeQuery();
            if (rs.first()) {
                ret = rs.getString(1);
            }
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(rs, stmt);
        }
        return ret;
    }

    /**
     * For SELECT queries that will return an array of integers
     * Example: getArrayInteger(mysql, "SELECT user_id FROM users WHERE age=?", 21);
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return ArrayList<Integer> with integers from the first column of results (could be empty)
     */
    public static ArrayList<Integer> getArrayInteger(Connection sql, String query, Object... params) {
        ArrayList<Integer> ret = new ArrayList<Integer>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = sql.prepareStatement(query);
            setStatementParams(stmt, params);
            rs = stmt.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                ret.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(rs, stmt);
        }

        return ret;
    }

    /**
     * For SELECT queries that will return an array of strings
     * Example: getArrayString(mysql, "SELECT name FROM users WHERE age=?", 21);
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return ArrayList<String> with strings from the first column of results (could be empty)
     */
    public static ArrayList<String> getArrayString(Connection sql, String query, Object... params) {
        ArrayList<String> ret = new ArrayList<String>();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = sql.prepareStatement(query);
            setStatementParams(stmt, params);
            rs = stmt.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                ret.add(rs.getString(1));
            }
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(rs, stmt);
        }

        return ret;
    }

    /**
     * For INSERT/UPDATE/DELETE queries
     * Example: execute(mysql, "DELETE FROM users WHERE age=?", 21);
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return row count for DML queries, otherwise -1
     */
    public static int execute(Connection sql, String query, Object... params) {
        int ret = -1;
        PreparedStatement stmt = null;
        try {
            stmt = sql.prepareStatement(query);
            setStatementParams(stmt, params);
            ret = stmt.executeUpdate();
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(null, stmt);
        }
        return ret;
    }

    /**
     * INSERT one row in a table using column names and values from Map
     *
     * @param sql Connection to a database
     * @param table Table's name
     * @param data Map with column names as keys and column values as map's values
     * @return true if the query was successful, false otherwise.
     */
    public static boolean insert(Connection sql, String table, Map<String, Object> data) {
        String columns[] = new String[data.size()];
        Object values[] = new Object[data.size()];

        // Have to use entrySet to guarantee the order
        int i = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            columns[i] = entry.getKey();
            values[i] = entry.getValue();
            i++;
        }

        String query = "INSERT INTO " + table
                + "(" + String.join(",", columns) + ") VALUES "
                + "(" + String.join(",", Collections.nCopies(data.size(), "?")) + ")";

        return execute(sql, query, values) > 0;
    }

    /**
     * INSERT one row in a table, return the AUTO_INCREMENT value
     * Example: insertAutoIncrement(mysql, "INSERT INTO users(name,age) VALUES (?,?)", "Alex", 21);
     * @param sql Connection to database
     * @param query SQL query
     * @param params Parameters for query
     * @return Value of AUTO_INCREMENT column, otherwise 0
     */
    public static int insertAutoIncrement(Connection sql, String query, Object... params) {
        int id = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = sql.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setStatementParams(stmt, params);
            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.first()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            printException(e, query);
        } finally {
            finishSQL(rs, stmt);
        }
        return id;
    }

    /**
     * UPDATE table, change multiple columns in one query
     * Example: update(mysql, "users", data, "user_id", 21);
     * @param sql Connection to database
     * @param table Table's name
     * @param data Map with column names as keys and column values as map's values
     * @param whereKey Column name for WHERE
     * @param whereValue Column value for WHERE
     * @return Number of rows that were updated
     */
    public static int update(Connection sql, String table, Map<String, Object> data, String whereKey, Object whereValue) {
        String columns[] = new String[data.size()];
        Object values[] = new Object[data.size() + 1]; // +1 for whereValue

        // Have to use entrySet to guarantee the order
        int i = 0;
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            columns[i] = entry.getKey();
            values[i] = entry.getValue();
            i++;
        }
        values[i] = whereValue;

        String query = "UPDATE " + table
                + " SET " + String.join("=?,", columns) + "=?"
                + " WHERE " + whereKey + "=?";

        return OneLineSQL.execute(sql, query, values);
    }

    /**
     * Close ResultSet and Statement
     * @param rs ResultSet
     * @param stmt Statement
     */
    public static void finishSQL(ResultSet rs, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                printException(e, null);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                printException(e, null);
            }
        }
    }

    public static void setStatementParams(PreparedStatement stmt, Object params[]) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Integer) {
                stmt.setInt(i + 1, (Integer) params[i]);
            } else if (params[i] instanceof String) {
                stmt.setString(i + 1, (String) params[i]);
            } else if (params[i] instanceof Long) {
                stmt.setLong(i + 1, (Long) params[i]);
            } else if (params[i] instanceof Boolean) {
                stmt.setBoolean(i + 1, (Boolean) params[i]);
            }
        }
    }

    private static void printException(SQLException e, String query) {
        System.err.println(e);
        if (query != null) {
            System.err.println("Query: " + query);
        }
    }
}
