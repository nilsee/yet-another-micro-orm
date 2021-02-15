package no.dataingenioer.yamo.core;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public interface MicroORM {

    // Create

    /**
     *
     * @param sql
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    <T> boolean insertQuery(String sql, T entity) throws
            SQLException, IllegalAccessException;

    // Read

    /**
     *
     * @param sql
     * @param type
     * @param <T>
     * @return
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    <T> List<T> selectQuery(String sql, Class<T> type) throws
            SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    /**
     *
     * @param sql
     * @param type
     * @param id
     * @param <T>
     * @return
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    <T> T selectQuery(String sql, Class<T> type, int id) throws
            SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    // Update

    /**
     *
     * @param sql
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    <T> boolean updateQuery(String sql, T entity) throws
            SQLException, IllegalAccessException;

    // Delete

    /**
     *
     * @param sql
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    <T> boolean deleteQuery(String sql, T entity) throws
            SQLException, IllegalAccessException;

}
