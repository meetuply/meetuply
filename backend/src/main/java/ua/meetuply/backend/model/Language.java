package ua.meetuply.backend.model;

public class Language {

    public Language () {}

    public Language(Integer languageId, String name) {
        this.languageId = languageId;
        this.name = name;
    }

    private Integer languageId;
    private String name;

    public Integer getLanguageId() {
        return languageId;
    }
    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
