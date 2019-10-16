package ua.meetuply.backend.dao;


import java.util.List;

public interface IDAO<T> {

    T get(Integer id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(Integer id);
}