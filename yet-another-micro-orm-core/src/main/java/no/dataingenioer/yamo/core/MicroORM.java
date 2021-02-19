package no.dataingenioer.yamo.core;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
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
    <T> T insert(String sql, T entity) throws
            SQLException, IllegalAccessException;

    // Read

    /**
     *
     * @param <T>
     * @param sql
     * @param type
     * @return
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    <T> Collection<T> select(String sql, Class<T> type) throws
            SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

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
    <T> T selectSingle(String sql, Class<T> type) throws
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
    <T> void update(String sql, T entity) throws
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
    <T> void delete(String sql, T entity) throws
            SQLException, IllegalAccessException;

}
