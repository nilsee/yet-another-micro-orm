package no.dataingenioer.yamo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotation contains mapping metadata for database table column
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * Used to rename the object field if the database column has another name
     * @return name of database table
     */
    String name();
}
