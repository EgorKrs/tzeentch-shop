package com.loneliness.util.search;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Searcher {
     <T>List<T> search(final List<SearchCriteria> params, final Class<T> tClass);
     <T>List<T> search(final SearchCriteria[] params, final Class<T> tClass);
     <T> T search(final SearchCriteria params, final Class<T> tClass);
}
