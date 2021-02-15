package no.dataingenioer.yamo;

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

import no.dataingenioer.yamo.utils.ConnectionSettings;

/**
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public class YetAnotherMicroORM implements MicroORM {

    /**
     *
     */
    private ConnectionSettings settings;

    /**
     *
     * @param settings
     * @throws Exception
     */
    public YetAnotherMicroORM(ConnectionSettings settings) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.settings = settings;
        Class.forName(settings.getDriver()).newInstance();
    }

    /**
     *
     * @return Open database connection
     * @throws Exception
     */
    private Connection getConnection() throws SQLException {
        Connection myConnection = DriverManager
                .getConnection( settings.getUrlWithDatabase(),
                                settings.getUsername(),
                                settings.getPassword());
        return myConnection;
    }

    /**
     *
     * @param sql
     * @param type
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> selectQuery(String sql, Class<T> type)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        List<T> results = new LinkedList<T>();

        Connection myConnection = getConnection();
        Statement myStatement = myConnection.createStatement();
        ResultSet resultSet = myStatement.executeQuery(sql);

        Field[] fields = type.getFields();
        while (resultSet.next())
        {
            T entity = type.getConstructor().newInstance();
            for (Field field : fields)
            {
                String fieldName = field.getName();
                Object colum = resultSet.getObject(fieldName);
                field.set(entity, colum);
            }
            results.add(entity);
        }

        myConnection.close();

        return results;
    }

    public <T> T selectQuery(String sql, Class<T> type, int id)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        return null;
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
    public <T> boolean insertQuery(String sql, T entity)
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
    public <T> boolean updateQuery(String sql, T entity)
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
    public <T> boolean deleteQuery(String sql, T entity)
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
    private <T> boolean writer(String sql, T entity) throws SQLException, IllegalAccessException
    {
        Parameters parameters = Parameters.parse(sql);

        Class<T> type = (Class<T>) entity.getClass();
        Field[] fields = type.getFields();
        for (Field field : fields)
        {
            String name = field.getName();
            Object value = field.get(entity);
            parameters.put(name, value);
        }

        Connection myConnection = getConnection();
        PreparedStatement myStatement = myConnection.prepareStatement(parameters.getSQL());
        parameters.apply(myStatement);

        boolean result = myStatement.execute();

        myConnection.close();

        return result;
    }
}
