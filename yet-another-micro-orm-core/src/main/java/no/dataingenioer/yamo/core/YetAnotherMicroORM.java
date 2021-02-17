package no.dataingenioer.yamo.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.httprpc.sql.Parameters;

import no.dataingenioer.yamo.core.annotations.Column;
import no.dataingenioer.yamo.core.annotations.Exclude;
import no.dataingenioer.yamo.core.utils.ConnectionSettings;

/**
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public final class YetAnotherMicroORM implements MicroORM
{
    /**
     *
     */
    private ConnectionSettings settings;

    /**
     *
     * @param settings
     * @throws Exception
     */
    public YetAnotherMicroORM(ConnectionSettings settings)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.settings = settings;
        Class.forName(settings.getDriver()).newInstance();
    }

    /**
     *
     * @param sql
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> select(String sql, Class<T> type)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        List<T> results = new LinkedList<T>();

        Connection myConnection = getConnection();
        Statement myStatement = myConnection.createStatement();
        ResultSet resultSet = myStatement.executeQuery(sql);

        Field[] fields = type.getDeclaredFields();
        while (resultSet.next())
        {
            T entity = type.getConstructor().newInstance();
            for (Field field : fields)
            {
                if( ! field.isAnnotationPresent(Exclude.class) ) {

                    String fieldName = getFieldName(field);
                    Object colum = resultSet.getObject(fieldName);
                    field.setAccessible(true);
                    field.set(entity, colum);
                }
            }
            results.add(entity);
        }

        myConnection.close();

        return results;
    }

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
    public <T> T selectSingle(String sql, Class<T> type)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {

        Connection myConnection = getConnection();
        Statement myStatement = myConnection.createStatement();
        ResultSet resultSet = myStatement.executeQuery(sql);

        Field[] fields = type.getDeclaredFields();
        T entity = null;
        if (resultSet.next()) // Only care about first entry
        {
            entity = type.getConstructor().newInstance();
            for (Field field : fields)
            {
                if( ! field.isAnnotationPresent(Exclude.class) )
                {
                    String fieldName = getFieldName(field);
                    Object colum = resultSet.getObject(fieldName);
                    field.setAccessible(true);
                    field.set(entity, colum);
                }
            }
        }

        myConnection.close();

        return entity;
    }

    /**
     *
     * @param sql
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public <T> boolean insert(String sql, T entity)
            throws SQLException, IllegalAccessException
    {
        return writer(sql, entity);
    }

    /**
     *
     * @param sql
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public <T> boolean update(String sql, T entity)
            throws SQLException, IllegalAccessException
    {
        return this.writer(sql, entity);
    }

    /**
     *
     * @param sql
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public <T> boolean delete(String sql, T entity)
            throws SQLException, IllegalAccessException
    {
        return writer(sql, entity);
    }

    /**
     *
     * @return Open database connection
     * @throws Exception
     */
    private Connection getConnection()
            throws SQLException
    {
        Connection myConnection = DriverManager.getConnection(
                        settings.getUrlWithDatabase(),
                        settings.getUsername(),
                        settings.getPassword());
        return myConnection;
    }

    /**
     *
     * @param sql
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private <T> boolean writer(String sql, T entity)
            throws SQLException, IllegalAccessException
    {

        Parameters parameters = Parameters.parse(sql);

        Class<T> type = (Class<T>) entity.getClass();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields)
        {
            field.setAccessible(true);
            if( ! field.isAnnotationPresent(Exclude.class)) {

                String name = getFieldName(field);
                Object value = field.get(entity);
                parameters.put(name, value);
            }
        }

        Connection myConnection = getConnection();
        PreparedStatement myStatement = myConnection.prepareStatement(parameters.getSQL());
        parameters.apply(myStatement);

        boolean result = myStatement.execute();

        myConnection.close();

        return result;
    }

    /**
     *
     * @param field
     * @return
     */
    private String getFieldName(Field field)
    {

        Column column = field.getAnnotation(Column.class);
        if(column != null)
        {
            String name = column.name();
            if (name != null)
            {
                return name;
            }
        }

        return field.getName();
    }
}