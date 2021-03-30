package no.dataingenioer.yamo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a integer field as the required primary key of the database talble.
 *
 * Is required for an entity
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
}
