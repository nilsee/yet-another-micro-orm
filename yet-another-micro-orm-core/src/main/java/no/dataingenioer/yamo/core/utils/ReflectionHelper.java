package no.dataingenioer.yamo.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.SQLException;

import no.dataingenioer.yamo.core.annotations.Column;
import no.dataingenioer.yamo.core.annotations.Entity;
import no.dataingenioer.yamo.core.annotations.Exclude;
import no.dataingenioer.yamo.core.annotations.Id;
import no.dataingenioer.yamo.core.repository.AbstractEntity;

/**
 * Helper methods to extract metadata from entity annotations using reflection
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public class ReflectionHelper {

    /**
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> String getTableName(Class<T> type) {

        Entity entity = type.getAnnotation(Entity.class);
        if(entity != null) {

            return entity.tableName();
        }

        return type.getSimpleName();
    }

    /**
     *
     * @param field
     * @return
     */
    public static String getColumnName(Field field) {

        Column column = field.getAnnotation(Column.class);
        if(column != null) {

            String name = column.name();
            if (name != null) {

                return name;
            }
        }

        return field.getName();
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
    public static <T> void addDataToFields(ResultSet resultSet, Field[] fields, T entity)
            throws SQLException, IllegalAccessException {

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
     * @param type
     * @param <T>
     * @return
     */
    public static <T> Field [] getAllFields(Class<T> type) {

        if(type == null) { // Recursice stop step

            return new Field[0];
        }

        Field [] resultSuper = getAllFields(type.getSuperclass()); // Recursiv call to super class

        Field [] resultThis = type.getDeclaredFields();

        return  concatenate(resultSuper, resultThis);
    }

    /**
     *
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> T[] concatenate(T[] a, T[] b) {

        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);

        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

}
