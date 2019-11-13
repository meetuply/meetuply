package ua.meetuply.backend.service;

import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.IFilterDAO;
import ua.meetuply.backend.model.Filter;
import javax.annotation.Resource;
import java.util.List;

@Component
public class FilterServiceImpl implements FilterService {

    @Resource(name = "filterDAO")
    private IFilterDAO<Filter> filterDao;

    @Override
    public Filter getFilter(Integer filterId) {
        return filterDao.get(filterId);
    }

    @Override
    public void createFilter(Filter filter) {
        filterDao.save(filter);
    }

    @Override
    public List<Filter> getUsersFilter(Integer uid) {
        return filterDao.getUsersFilters(uid);
    }

    @Override
    public void updateFilter(Filter filter) {
        filterDao.update(filter);
    }

    @Override
    public void deleteFilter(Integer filterId) {
        filterDao.delete(filterId);
    }

    @Override
    public List<Filter> getAllFilters() {
        return filterDao.getAll();
    }

    @Override
    public int saveAndGetCreatedFilter(Filter filter) {
        return this.filterDao.saveAndGetCreatedFilter(filter);
    }

}
