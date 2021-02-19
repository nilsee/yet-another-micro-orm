package no.dataingenioer.yamo.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.httprpc.sql.Parameters;

import no.dataingenioer.yamo.core.annotations.Column;
import no.dataingenioer.yamo.core.annotations.Exclude;
import no.dataingenioer.yamo.core.annotations.Id;
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
     * @param <T>
     * @param sql
     * @param type
     * @return
     * @throws Exception
     */
    public <T> Collection<T> select(String sql, Class<T> type)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        List<T> results = new LinkedList<T>();
        Connection myConnection = getConnection();
        Statement myStatement = myConnection.createStatement();
        ResultSet resultSet = myStatement.executeQuery(sql);
        Field[] fields = type.getDeclaredFields();

        while (resultSet.next()) {

            T entity = type.getConstructor().newInstance();
            addDataToFields(resultSet, fields, entity);
            results.add(entity);
        }

        myConnection.close();

        return results;
    }

    /**
     *
     * @param resultSet
     * @param fields
     * @param entity
     * @param <T>
     * @throws SQLException
     * @throws IllegalAccessException
     */
    private <T> void addDataToFields(ResultSet resultSet, Field[] fields, T entity) throws SQLException, IllegalAccessException {
        for (Field field : fields) {

            if( ! field.isAnnotationPresent(Exclude.class) ) {

                String fieldName = getColumnName(field);
                Object colum = resultSet.getObject(fieldName);
                field.setAccessible(true);
                field.set(entity, colum);
            }
        }
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
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Connection myConnection = getConnection();
        Statement myStatement = myConnection.createStatement();
        ResultSet resultSet = myStatement.executeQuery(sql);
        Field[] fields = type.getDeclaredFields();
        T entity = null;

        if (resultSet.next()) { // Only care about first entry

            entity = type.getConstructor().newInstance();
            addDataToFields(resultSet, fields, entity);
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
    public <T> T insert(String sql, T entity)
            throws SQLException, IllegalAccessException {

       return this.writer(sql, entity, true);
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
    public <T> void update(String sql, T entity)
            throws SQLException, IllegalAccessException {

        this.writer(sql, entity);
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
    public <T> void delete(String sql, T entity)
            throws SQLException, IllegalAccessException {

        writer(sql, entity);
    }

    /**
     *
     * @return Open database connection
     * @throws Exception
     */
    private Connection getConnection()
            throws SQLException {

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
    private <T> void writer(String sql, T entity)
            throws SQLException, IllegalAccessException {

        Parameters parameters = Parameters.parse(sql);
        Class<T> type = (Class<T>) entity.getClass();
        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {

            if( ! field.isAnnotationPresent(Exclude.class)) {

                field.setAccessible(true);
                String name = getColumnName(field);
                Object value = field.get(entity);
                parameters.put(name, value);
            }
        }

        Connection myConnection = getConnection();
        PreparedStatement myStatement = myConnection.prepareStatement(parameters.getSQL());
        parameters.apply(myStatement);
        myStatement.executeUpdate();
        myConnection.close();
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
    private <T> T writer(String sql, T entity, boolean addId)
            throws SQLException, IllegalAccessException {

        Parameters parameters = Parameters.parse(sql);
        Class<T> type = (Class<T>) entity.getClass();
        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {

            if( ! field.isAnnotationPresent(Exclude.class)) {

                field.setAccessible(true);
                String name = getColumnName(field);
                Object value = field.get(entity);
                parameters.put(name, value);
            }
        }

        Connection myConnection = getConnection();

        PreparedStatement myStatement;
        if(addId) {

           myStatement = myConnection.prepareStatement(parameters.getSQL(), Statement.RETURN_GENERATED_KEYS);
        } else {

            myStatement = myConnection.prepareStatement(parameters.getSQL());
        }

        parameters.apply(myStatement);

        myStatement.executeUpdate();

        if(addId) {

            ResultSet rs = myStatement.getGeneratedKeys();
            if (rs.next()) {

                int id = rs.getInt(1);
                for (Field field : fields) {

                    if (field.isAnnotationPresent(Id.class)) {

                        field.setAccessible(true);
                        field.set(entity, id);
                    }
                }
            }
        }

        myConnection.close();

        return entity;
    }

    /**
     *
     * @param field
     * @return
     */
    private String getColumnName(Field field) {

        Column column = field.getAnnotation(Column.class);
        if(column != null) {

            String name = column.name();
            if (name != null) {

                return name;
            }
        }
        return field.getName();
    }

}