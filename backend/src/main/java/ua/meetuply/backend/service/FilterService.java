package ua.meetuply.backend.service;

import ua.meetuply.backend.model.Filter;

import java.util.List;

public interface FilterService {

    Filter getFilter(Integer filterId);

    void createFilter(Filter filter);

    List<Filter> getUsersFilter(Integer uid);

    void updateFilter(Filter filter);

    void deleteFilter(Integer id);

    List<Filter> getAllFilters();
}
