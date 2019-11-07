package ua.meetuply.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.meetuply.backend.dao.LanguageDAO;
import ua.meetuply.backend.model.Language;

import java.util.List;

@Component
public class LanguageService {

    @Autowired
    LanguageDAO languageDAO;

    public Language get(Integer id) {
        return languageDAO.get(id);
    }

    public Language get(String name) {
        return languageDAO.getLanguageByName(name);
    }

    public void create(Language topic) {
        languageDAO.save(topic);
    }

    public void update(Language topic) {
        languageDAO.update(topic);
    }

    public void delete(Integer id) {
        languageDAO.delete(id);
    }

    public List<Language> getAll() {
        return languageDAO.getAll();
    }


    public List<Language> getUserLanguages(Integer id) {
        return languageDAO.getUserLanguages(id);
    }

}
