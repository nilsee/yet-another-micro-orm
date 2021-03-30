package no.dataingenioer.yamo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Optional metadata for mapping entity to database table
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    /* TODO: Verify table name in query */

    /**
     * Used to rename the object class anme if the database table has another name
     * @return name of database table
     */
    String tableName();
}
