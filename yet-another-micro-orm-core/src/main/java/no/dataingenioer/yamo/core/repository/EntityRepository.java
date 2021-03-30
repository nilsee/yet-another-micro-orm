package no.dataingenioer.yamo.core.repository;

import java.util.Collection;

import no.dataingenioer.yamo.core.query.QueryBuilder;

/**
 * Example on  how to implement a default repository around yamo.
 *
 * @param <T>
 * @Author Nils Einar Eide
 * @Email nils@dataingenioer.no
 */
public interface EntityRepository<T extends AbstractEntity> {

    /**
     *
     * @param entity
     * @return
     */
    T create(T entity);

    /**
     *
     * @param id
     * @return
     */
    T read(int id);

    /**
     *
     * @return
     */
    Collection<T> readAll();

    /**
     *
     * @param entity
     * @return
     */
    boolean update(T entity);

    /**
     *
     * @param entity
     * @return
     */
    boolean delete(T entity);

    /**
     *
     * @return
     */
    QueryBuilder getQueryBuilder();

}
