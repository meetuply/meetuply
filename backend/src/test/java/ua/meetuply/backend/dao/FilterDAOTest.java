package ua.meetuply.backend.dao;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.meetuply.backend.model.Filter;
import ua.meetuply.backend.model.Topic;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.meetuply.backend.dao.FilterDAO.CREATE_FILTER_TOPIC_QUERY;
import static ua.meetuply.backend.dao.FilterDAO.GET_ALL_FILTERS_QUERY;
import static ua.meetuply.backend.dao.FilterDAO.GET_FILTER_QUERY;
import static ua.meetuply.backend.dao.FilterDAO.GET_USERS_FILTERS_QUERY;

@RunWith(MockitoJUnitRunner.class)
public class FilterDAOTest {

    private static final int EXISTING_FILTER_ID = 92;
    private static final int NON_EXISTING_FILTER_ID = 109;
    private static final int USER_ID = 21;
    private static final int TOPIC_ID = 555;

    @Spy
    @InjectMocks
    private FilterDAO testInstance;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private Filter filter;
    private Filter filter1;
    private Filter filter2;

    @Before
    public void setUp() {
        filter = new Filter();
        filter.setId(EXISTING_FILTER_ID);
        Topic topic = new Topic();
        topic.setTopicId(TOPIC_ID);
        filter.setTopics(singletonList(topic));
        filter1 = new Filter();
        filter2 = new Filter();

        List<Object> getFilterByIdQueryParams = singletonList(EXISTING_FILTER_ID);
        List<Object> getUserFiltersQueryParams = singletonList(USER_ID);

        doReturn(EXISTING_FILTER_ID).when(testInstance).getSavedFilterId(filter);
        when(jdbcTemplate.query(GET_FILTER_QUERY, getFilterByIdQueryParams.toArray(), testInstance))
                .thenReturn(singletonList(filter));
        when(jdbcTemplate.query(GET_USERS_FILTERS_QUERY, getUserFiltersQueryParams.toArray(), testInstance))
                .thenReturn(singletonList(filter));
        when(jdbcTemplate.query(GET_ALL_FILTERS_QUERY, testInstance))
                .thenReturn(Arrays.asList(filter, filter1, filter2));
    }

    @Test
    public void shouldReturnFilterByIdWhenFilterExists() {
        Filter actual = testInstance.get(EXISTING_FILTER_ID);

        assertThat(actual).isEqualTo(filter);
    }

    @Test
    public void shouldReturnNullWhenFilterIdNotExists() {
        Filter actual = testInstance.get(NON_EXISTING_FILTER_ID);

        assertThat(actual).isNull();
    }

    @Test
    public void shouldReturnUserFilters() {
        List<Filter> actual = testInstance.getUsersFilters(USER_ID);

        assertThat(actual).containsExactly(filter);
    }

    @Test
    public void shouldReturnAllFilters() {
        List<Filter> actual = testInstance.getAll();

        assertThat(actual).containsExactly(filter, filter1, filter2);
    }

    @Test
    public void shouldSaveFilter() {
        testInstance.save(filter);

        verify(jdbcTemplate).update(CREATE_FILTER_TOPIC_QUERY, TOPIC_ID, EXISTING_FILTER_ID);
    }

}
