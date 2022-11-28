package net.yorksolutions.mattbauercapstonebe.modules;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private int index;
    private String prompt;
    private String responseType;
    @ElementCollection
    private List<String> responseOptions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public List<String> getResponseOptions() {
        return responseOptions;
    }

    public void setResponseOptions(List<String> responseOptions) {
        this.responseOptions = responseOptions;
    }
}
