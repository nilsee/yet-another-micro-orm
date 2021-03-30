package no.dataingenioer.yamo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark af class field to be ignored on persistence to the database
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Exclude {
}
