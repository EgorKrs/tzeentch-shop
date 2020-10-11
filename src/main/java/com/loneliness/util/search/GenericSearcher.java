package com.loneliness.util.search;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Repository
public class GenericSearcher implements Searcher {
    @PersistenceContext
    private EntityManager entityManager;




    @Override
    public <T> List<T> search(final List<SearchCriteria> params, Class<T> tClass) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Predicate predicate = builder.conjunction();
        final CriteriaQuery<T> query = builder.createQuery(tClass);


        SearchQueryCriteriaConsumer searchConsumer = new SearchQueryCriteriaConsumer(predicate, builder, query.from(tClass));
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public <T>List<T> search(SearchCriteria[] params, Class<T> tClass) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Predicate predicate = builder.conjunction();
        final CriteriaQuery<T> query = builder.createQuery(tClass);


        SearchQueryCriteriaConsumer  searchConsumer = new SearchQueryCriteriaConsumer(predicate, builder, query.from(tClass));
        for (SearchCriteria criteria :
                params) {
            searchConsumer.accept(criteria);
        }
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public <T> T search(SearchCriteria criteria, Class<T> tClass) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Predicate predicate = builder.conjunction();
        final CriteriaQuery<T> query = builder.createQuery(tClass);

        SearchQueryCriteriaConsumer  searchConsumer = new SearchQueryCriteriaConsumer(predicate, builder, query.from(tClass));
        searchConsumer.accept(criteria);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);
        return entityManager.createQuery(query).getSingleResult();
    }
}
