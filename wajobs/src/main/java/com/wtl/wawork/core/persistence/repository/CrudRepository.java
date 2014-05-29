package com.wtl.wawork.core.persistence.repository;

/**
 * Data access service for common CRUD operations
 * 
 * @author Weston Turney-Loos
 * 
 * @param <T>
 *            entity type to perform CRUD operations on.
 */
public interface CrudRepository<T> {

    /**
     * Persists a new entity
     * 
     * @param entity
     *            the entity to persist
     */
    public abstract void create(T entity);

    /**
     * Attempt to delete the given entity
     * 
     * @param entity
     *            the entity to be deleted
     */
    public abstract void delete(T entity);

    /**
     * Attempts to delete an entity of the implementing type with a given
     * primary key
     * 
     * @param primaryKey
     *            primary key of the entity to be deleted
     */
    public abstract void deleteWithPrimaryKey(Object primaryKey);

/**
	 * Find an entity with a given primary key
	 * 
	 * @param primaryKey
	 *            primary key of the {@}
	 * @return entity matching the given primary key, or null
	 *         if no match could be found
	 */
    public abstract T find(Object primaryKey);

    /**
     * @param entity
     *            the entity to update
     */
    public abstract void update(T entity);

}