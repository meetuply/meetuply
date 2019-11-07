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

    public void create(Language lang) {
        languageDAO.save(lang);
    }

    public void update(Language lang) {
        languageDAO.update(lang);
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

    public void updateUserLanguages(Integer userId, Iterable<Integer> languagesIds) {
        deleteUserLanguage(userId);
        for (Integer id: languagesIds) {
            addUserLanguage(userId, id);
        }
    }

    public void deleteUserLanguage(Integer userId) {
        languageDAO.deleteUserLanguages(userId);
    }

    public void addUserLanguage(Integer userId, Integer langId) {
        languageDAO.addUserLanguages(userId, langId);
    }

}
