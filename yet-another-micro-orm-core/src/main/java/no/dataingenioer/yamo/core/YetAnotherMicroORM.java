package no.dataingenioer.yamo.core;

import static no.dataingenioer.yamo.core.utils.ReflectionHelper.addDataToFields;
import static no.dataingenioer.yamo.core.utils.ReflectionHelper.getColumnName;

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

import no.dataingenioer.yamo.core.annotations.Exclude;
import no.dataingenioer.yamo.core.annotations.Id;
import no.dataingenioer.yamo.core.query.QueryBuilder;
import no.dataingenioer.yamo.core.configuration.ConnectionSettings;
import no.dataingenioer.yamo.core.utils.ReflectionHelper;

/**
 * Implementation of micro ORM
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public class YetAnotherMicroORM implements MicroORM
{
    /**
     *
     */
    protected ConnectionSettings settings;

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
        Field[] fields = ReflectionHelper.getAllFields(type); //type.getDeclaredFields();

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
     * @param queryBuilder
     * @param type
     * @param <T>
     * @return
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public <T> Collection<T> select(QueryBuilder queryBuilder, Class<T> type)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return select(queryBuilder.buildSelect(), type);
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
    public <T> T selectSingle(String sql, Class<T> type, int id)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        // TODO: Temporary hack
        sql = String.format(sql, id);

        Connection myConnection = getConnection();
        Statement myStatement = myConnection.createStatement();
        ResultSet resultSet = myStatement.executeQuery(sql);
        Field[] fields = ReflectionHelper.getAllFields(type); //type.getDeclaredFields();
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
     * @param queryBuilder
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
    public <T> T selectSingle(QueryBuilder queryBuilder, Class<T> type, int id)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        return selectSingle(queryBuilder.buildSelectSingle(), type, id);
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
     * @param queryBuilder
     * @param entity
     * @param <T>
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public <T> T insert(QueryBuilder queryBuilder, T entity)
            throws SQLException, IllegalAccessException {

        return insert(queryBuilder.buildInsert(), entity);
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

        this.writer(sql, entity, false);
    }

    /**
     *
     * @param queryBuilder
     * @param entity
     * @param <T>
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public <T> void update(QueryBuilder queryBuilder, T entity)
            throws SQLException, IllegalAccessException {

        update(queryBuilder.buildUpdate(), entity);
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

        writer(sql, entity, false);
    }

    /**
     *
     * @param queryBuilder
     * @param entity
     * @param <T>
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public <T> void delete(QueryBuilder queryBuilder, T entity)
            throws SQLException, IllegalAccessException {

        delete(queryBuilder.buildDelete(), entity);
    }

    /**
     *
     * @return Open database connection
     * @throws Exception
     */
    protected Connection getConnection()
            throws SQLException {

        Connection myConnection = DriverManager.getConnection(
                        settings.getUrl(),
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
    protected <T> T writer(String sql, T entity, boolean addId)
            throws SQLException, IllegalAccessException {

        Parameters parameters = Parameters.parse(sql);
        Class<T> type = (Class<T>) entity.getClass();
        Field[] fields = ReflectionHelper.getAllFields(type); //type.getDeclaredFields();

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

}