package no.dataingenioer.yamo.core.query;

import static no.dataingenioer.yamo.core.utils.ReflectionHelper.getColumnName;
import static no.dataingenioer.yamo.core.utils.ReflectionHelper.getTableName;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import no.dataingenioer.yamo.core.annotations.Exclude;
import no.dataingenioer.yamo.core.annotations.Id;
import no.dataingenioer.yamo.core.utils.ReflectionHelper;

/**
 * Build basic sql-statments based on entity name and fields.
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public final class SimpleSqlBuilder implements QueryBuilder {

    /**
     * Name of database table form entity name
     */
    private String tableName;

    /**
     * Name of the integer primary key of the table
     */
    private String idColumnName;

    /**
     * All other fields from the entity, other then ID
     */
    private List<String> columns;

    /**
     * Helper Enum to generate diffrent list on fields
     */
    private enum ColumnType {
        INSERT,
        UPDATE,
        DEFAULT
    }

    /**
     * Object without type description is meaningless
     */
    protected SimpleSqlBuilder() {
    }

    /**
     * @param type Type to be mapped by the SQL
     * @param <T>
     */
    public <T> SimpleSqlBuilder(Class<T> type) {

        init(type);
    }

    /**
     * @return INSERT statement based on fields in entity object
     */
    public String buildInsert() {

        return String.format("INSERT INTO %s ( %s ) VALUES ( %s );"
                , this.tableName
                , columns(ColumnType.DEFAULT)
                , columns(ColumnType.INSERT));
    }

    /**
     * @return SELECT statement based on fields in entity object
     */
    public String buildSelect() {

        return String.format("SELECT %s, %s FROM %s;"
                , idColumnName
                , columns(ColumnType.DEFAULT)
                , this.tableName );
    }

    /**
     * @return SELECT statement for primary key id based on fields in entity object
     */
    public String buildSelectSingle() {

        return String.format("SELECT %s, %s FROM %s WHERE %s;"
                , idColumnName
                , columns(ColumnType.DEFAULT)
                , this.tableName
                , getWhereClause());
    }

    /**
     * @return UPDATE statement based on fields in entity object
     */
    public String buildUpdate() {

        return String.format("UPDATE %s SET %s WHERE %s;"
                , this.tableName
                , columns(ColumnType.UPDATE)
                , getWhereClause());
    }

    /**
     * @return DELETE statement based on fields in entity object
     */
    public String buildDelete() {

        return String.format("DELETE FROM %s WHERE %s;"
                , this.tableName
                , getWhereClause());
    }

    @Override
    public String toString() {

        return String.format("%s\n%s\n%s\n%s\n%s\n"
                , buildInsert()
                , buildSelect()
                , buildSelectSingle()
                , buildUpdate()
                , buildDelete());
    }

    /**
     * Builds table and column names based on ehtity type definition
     * @param type
     * @param <T>
     */
    private <T> void init(Class<T> type)  {

        this.tableName = getTableName(type);

        this.columns = new LinkedList<String>();
        Field[] fields = ReflectionHelper.getAllFields(type); // type.getDeclaredFields();
        for (Field field : fields) {

            if( ! field.isAnnotationPresent(Exclude.class) ) {

                if (field.isAnnotationPresent(Id.class)) {

                    idColumnName = getColumnName(field);
                } else {

                    columns.add(getColumnName(field));
                }
            }
        }
    }

    /**
     * @param type
     * @return
     */
    private String columns(ColumnType type) {

        StringBuilder builder = new StringBuilder();

        for (String column: columns) {

            if(type == ColumnType.DEFAULT) {

                builder.append(column).append(", ");
            } else if(type == ColumnType.INSERT) {

                builder.append(":").append(column).append(", ");
            } else if(type == ColumnType.UPDATE){

                builder.append(column).append(" = :").append(column).append(", ");
            }

        }

        if(builder.length() > 0) {

            builder.deleteCharAt(builder.length() - 1); // Remove " "
        }

        if(builder.length() > 0) {

            builder.deleteCharAt(builder.length() - 1); // Remove ","
        }

        return builder.toString();
    }

    /**
     * @return
     */
    private String getWhereClause() {

        return String.format("%s = :%s"
                , idColumnName
                , idColumnName);
    }

}


