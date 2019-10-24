package ua.meetuply.backend.service;

import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.IDAO;
import ua.meetuply.backend.model.Filter;
import javax.annotation.Resource;
import java.util.List;

@Component
public class FilterServiceImpl implements FilterService {

    @Resource(name="filterDao")
    private IDAO<Filter> filterDao;

    @Override
    public Filter getFilter(Integer filterId) {
        return filterDao.get(filterId);
    }

    @Override
    public void createFilter(Filter filter) {
        filterDao.save(filter);
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

}
