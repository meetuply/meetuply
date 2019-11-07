package ua.meetuply.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.Language;
import ua.meetuply.backend.service.LanguageService;

import javax.validation.Valid;

@RequestMapping("api/languages")
@Transactional
@RestController
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping()
    public @ResponseBody
    Iterable<Language> getAllLanguages() {
        return languageService.getAll();
    }

    @GetMapping("/{languageId}")
    public Language getOneLanguage(@PathVariable("languageId") Integer languageId) {
        return languageService.get(languageId);
    }

    @PostMapping("/create")
    public ResponseEntity<Language> createNewLanguage(@Valid @RequestBody Language language) {
        languageService.create(language);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{languageId}")
    public ResponseEntity<Language> updateLanguage(@PathVariable("languageId") Integer languageId, @RequestBody Language language) {
        if (languageService.get(languageId) == null) {
            ResponseEntity.badRequest().build();
        }
        languageService.update(language);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{languageId}")
    public ResponseEntity<Language> deleteLanguage(@PathVariable("languageId") Integer languageId) {
        if (languageService.get(languageId) == null) {
            ResponseEntity.badRequest().build();
        }
        languageService.delete(languageId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("user/{userId}")
    public ResponseEntity updateUserLanguages(@PathVariable("userId") Integer userId, @RequestBody Iterable<Integer> languagesIds) {
        languageService.updateUserLanguages(userId, languagesIds);
        return ResponseEntity.ok().build();
    }
}