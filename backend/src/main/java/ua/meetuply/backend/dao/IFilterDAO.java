package ua.meetuply.backend.dao;

import java.util.List;

public interface IFilterDAO<Filter> extends IDAO<Filter>{

    List<Filter> getUsersFilters(Integer userId);

    int saveAndGetCreatedFilter(Filter filter);

}
