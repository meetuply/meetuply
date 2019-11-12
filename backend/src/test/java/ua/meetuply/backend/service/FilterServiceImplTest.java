package ua.meetuply.backend.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.meetuply.backend.dao.IFilterDAO;
import ua.meetuply.backend.model.Filter;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FilterServiceImplTest {

    private final static int FILTER_ID = 54;
    private final static int USER_ID = 21;

    @InjectMocks
    private FilterServiceImpl testInstance = new FilterServiceImpl();

    @Mock
    private IFilterDAO<Filter> filterDao;

    private Filter filter;
    private Filter filter1;
    private Filter filter2;

    @Before
    public void setUp() {
        filter = new Filter();
        filter.setId(FILTER_ID);
        filter1 = new Filter();
        filter2 = new Filter();

        when(filterDao.get(FILTER_ID)).thenReturn(filter);
        when(filterDao.getUsersFilters(USER_ID)).thenReturn(singletonList(filter));
        when(filterDao.getAll()).thenReturn(Arrays.asList(filter, filter1, filter2));
    }

    @Test
    public void shouldReturnFilterByFilterId() {
        Filter actual = testInstance.getFilter(FILTER_ID);

        assertThat(actual).isEqualTo(filter);
    }

    @Test
    public void shouldCreateFilter() {
        testInstance.createFilter(filter);

        verify(filterDao).save(filter);
    }

    @Test
    public void shouldUpdateFilter() {
        testInstance.updateFilter(filter);

        verify(filterDao).update(filter);
    }

    @Test
    public void shouldDeleteFilter() {
        testInstance.deleteFilter(FILTER_ID);

        verify(filterDao).delete(FILTER_ID);
    }

    @Test
    public void shouldReturnUserFilters() {
        List<Filter> actual = testInstance.getUsersFilter(USER_ID);

        assertThat(actual).containsExactly(filter);
    }

    @Test
    public void shouldReturnAllFilters() {
        List<Filter> actual = testInstance.getAllFilters();

        assertThat(actual).containsExactly(filter, filter1, filter2);
    }

}