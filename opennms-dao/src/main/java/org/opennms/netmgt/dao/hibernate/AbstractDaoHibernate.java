/*
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 2006 The OpenNMS Group, Inc. All rights
 * reserved.
 * OpenNMS(R) is a derivative work, containing both original code, included
 * code and modified
 * code that was published under the GNU General Public License. Copyrights
 * for modified
 * and included code are below.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * For more information contact:
 *      OpenNMS Licensing <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 */
package org.opennms.netmgt.dao.hibernate;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.opennms.netmgt.dao.OnmsDao;
import org.opennms.netmgt.model.OnmsCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * <p>Abstract AbstractDaoHibernate class.</p>
 *
 * @author ranger
 * @version $Id: $
 */
public abstract class AbstractDaoHibernate<T, K extends Serializable> extends HibernateDaoSupport implements OnmsDao<T, K> {

    Class<T> m_entityClass;

    /**
     * <p>Constructor for AbstractDaoHibernate.</p>
     *
     * @param entityClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @param <K> a K object.
     */
    public AbstractDaoHibernate(Class<T> entityClass) {
        super();
        m_entityClass = entityClass;
    }

    /** {@inheritDoc} */
    public void initialize(Object obj) {
        getHibernateTemplate().initialize(obj);
    }

    /**
     * <p>flush</p>
     */
    public void flush() {
        getHibernateTemplate().flush();
    }

    /**
     * <p>clear</p>
     */
    public void clear() {
        getHibernateTemplate().clear();
    }

    /**
     * <p>evict</p>
     *
     * @param entity a T object.
     */
    public void evict(T entity) {
        getHibernateTemplate().evict(entity);
    }

    /**
     * <p>merge</p>
     *
     * @param entity a T object.
     */
    public void merge(T entity) {
        getHibernateTemplate().merge(entity);
    }

    /**
     * <p>find</p>
     *
     * @param query a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    @SuppressWarnings("unchecked")
    public List<T> find(String query) {
        return getHibernateTemplate().find(query);
    }

    /**
     * <p>find</p>
     *
     * @param query a {@link java.lang.String} object.
     * @param values a {@link java.lang.Object} object.
     * @return a {@link java.util.List} object.
     */
    @SuppressWarnings("unchecked")
    public List<T> find(String query, Object... values) {
        return getHibernateTemplate().find(query, values);
    }
    
    /**
     * <p>findObjects</p>
     *
     * @param clazz a {@link java.lang.Class} object.
     * @param query a {@link java.lang.String} object.
     * @param values a {@link java.lang.Object} object.
     * @param <S> a S object.
     * @return a {@link java.util.List} object.
     */
    @SuppressWarnings("unchecked")
    public <S> List<S> findObjects(Class<S> clazz, String query, Object... values) {
    	final List notifs = getHibernateTemplate().find(query, values);
        return notifs;
    }

    /**
     * <p>queryInt</p>
     *
     * @param query a {@link java.lang.String} object.
     * @return a int.
     */
    protected int queryInt(final String query) {
        HibernateCallback<Number> callback = new HibernateCallback<Number>() {

            public Number doInHibernate(Session session) throws HibernateException {
                return (Number)session.createQuery(query).uniqueResult();
            }

        };

        return getHibernateTemplate().execute(callback).intValue();
    }

    /**
     * <p>queryInt</p>
     *
     * @param queryString a {@link java.lang.String} object.
     * @param args a {@link java.lang.Object} object.
     * @return a int.
     */
    protected int queryInt(final String queryString, final Object... args) {
        HibernateCallback<Number> callback = new HibernateCallback<Number>() {

            public Number doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(queryString);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                return (Number)query.uniqueResult();
            }

        };

        return getHibernateTemplate().execute(callback).intValue();
    }

    //TODO: This method duplicates below impl, delete this
    /**
     * <p>findUnique</p>
     *
     * @param query a {@link java.lang.String} object.
     * @return a T object.
     */
    protected T findUnique(final String query) {
        return findUnique(m_entityClass, query);
    }

    /**
     * <p>findUnique</p>
     *
     * @param queryString a {@link java.lang.String} object.
     * @param args a {@link java.lang.Object} object.
     * @return a T object.
     */
    protected T findUnique(final String queryString, final Object... args) {
        return findUnique(m_entityClass, queryString, args);
    }
    
    /**
     * <p>findUnique</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param queryString a {@link java.lang.String} object.
     * @param args a {@link java.lang.Object} object.
     * @param <S> a S object.
     * @return a S object.
     */
    protected <S> S findUnique(final Class <? extends S> type, final String queryString, final Object... args) {
        HibernateCallback<S> callback = new HibernateCallback<S>() {

            public S doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(queryString);
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
                Object result = query.uniqueResult();
                return result == null ? null : type.cast(result);
            }

        };
//      logger.debug(String.format("findUnique(%s, %s, %s) = %s", type, queryString, Arrays.toString(args), result));
//      Assert.isTrue(result == null || type.isInstance(result), "Expected "+result+" to an instance of "+type+" but is "+(result == null ? null : result.getClass()));
        return getHibernateTemplate().execute(callback);
    }


    /**
     * <p>countAll</p>
     *
     * @return a int.
     */
    public int countAll() {
        return queryInt("select count(*) from " + m_entityClass.getName());
    }

    /**
     * <p>delete</p>
     *
     * @param entity a T object.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public void delete(T entity) throws DataAccessException {
        getHibernateTemplate().delete(entity);
    }
    
    /**
     * <p>deleteAll</p>
     *
     * @param entities a {@link java.util.Collection} object.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public void deleteAll(Collection<T> entities) throws DataAccessException {
        getHibernateTemplate().deleteAll(entities);
    }

    /**
     * <p>findAll</p>
     *
     * @return a {@link java.util.List} object.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public List<T> findAll() throws DataAccessException {
        return getHibernateTemplate().loadAll(m_entityClass);
    }
    
    /**
     * <p>findMatchingObjects</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param onmsCrit a {@link org.opennms.netmgt.model.OnmsCriteria} object.
     * @param <S> a S object.
     * @return a {@link java.util.List} object.
     */
    @SuppressWarnings("unchecked")
    public <S> List<S> findMatchingObjects(final Class<S> type, final OnmsCriteria onmsCrit ) {
        onmsCrit.resultsOfType(type);
        
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria attachedCrit = onmsCrit.getDetachedCriteria().getExecutableCriteria(session);
                if (onmsCrit.getFirstResult() != null) {
                    attachedCrit.setFirstResult(onmsCrit.getFirstResult());
                }
                
                if (onmsCrit.getMaxResults() != null) {
                    attachedCrit.setMaxResults(onmsCrit.getMaxResults());
                }
                
                return attachedCrit.list();
            }
            
        };
        return getHibernateTemplate().executeFind(callback);
    }
    

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<T> findMatching(final OnmsCriteria onmsCrit) throws DataAccessException {
        onmsCrit.resultsOfType(m_entityClass); //FIXME: why is this here?
        
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria attachedCrit = onmsCrit.getDetachedCriteria().getExecutableCriteria(session);
                if (onmsCrit.getFirstResult() != null) {
                    attachedCrit.setFirstResult(onmsCrit.getFirstResult());
                }
                
                if (onmsCrit.getMaxResults() != null) {
                    attachedCrit.setMaxResults(onmsCrit.getMaxResults());
                }
                
                return attachedCrit.list();
                
            }
            
        };
        return getHibernateTemplate().executeFind(callback);
    }
    
    /** {@inheritDoc} */
    public int countMatching(final OnmsCriteria onmsCrit) throws DataAccessException {
        HibernateCallback<Integer> callback = new HibernateCallback<Integer>() {

            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria attachedCrit = onmsCrit.getDetachedCriteria().getExecutableCriteria(session)
                    .setProjection(Projections.rowCount());
                
                return (Integer)attachedCrit.uniqueResult();
                
            }
            
        };
        return getHibernateTemplate().execute(callback).intValue();
    }
    
    /**
     * <p>bulkDelete</p>
     *
     * @param hql a {@link java.lang.String} object.
     * @param values an array of {@link java.lang.Object} objects.
     * @return a int.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public int bulkDelete(String hql, Object[] values ) throws DataAccessException {
        return getHibernateTemplate().bulkUpdate(hql, values);
    }
    
    /**
     * <p>get</p>
     *
     * @param id a K object.
     * @return a T object.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public T get(K id) throws DataAccessException {
        return m_entityClass.cast(getHibernateTemplate().get(m_entityClass, id));
    }

    /**
     * <p>load</p>
     *
     * @param id a K object.
     * @return a T object.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public T load(K id) throws DataAccessException {
        return m_entityClass.cast(getHibernateTemplate().load(m_entityClass, id));
    }

    /**
     * <p>save</p>
     *
     * @param entity a T object.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public void save(T entity) throws DataAccessException {
        getHibernateTemplate().save(entity);
    }

    /**
     * <p>saveOrUpdate</p>
     *
     * @param entity a T object.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public void saveOrUpdate(T entity) throws DataAccessException {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    /**
     * <p>update</p>
     *
     * @param entity a T object.
     * @throws org.springframework.dao.DataAccessException if any.
     */
    public void update(T entity) throws DataAccessException {
        getHibernateTemplate().update(entity);
    }

}
