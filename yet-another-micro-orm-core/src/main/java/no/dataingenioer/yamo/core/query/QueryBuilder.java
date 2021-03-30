package no.dataingenioer.yamo.core.query;

/**
 * Interface for building queries for a given object-table mapping
 *
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public interface QueryBuilder {

    /**
     * @return INSERT statment based on fields in entity object
     */
    String buildInsert();

    /**
     * @return SELECT statment based on fields in entity object
     */
   String buildSelect();

    /**
     * @return SELECT statment for primary key id based on fields in entity object
     */
    String buildSelectSingle();

    /**
     * @return UPDATE statment based on fields in entity object
     */
    String buildUpdate();

    /**
     * @return DELETE statment based on fields in entity object
     */
    String buildDelete();
}
