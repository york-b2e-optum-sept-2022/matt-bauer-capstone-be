package net.yorksolutions.mattbauercapstonebe.modules;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        if (index < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.index = index;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        if (prompt.length() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.prompt = prompt;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        if (responseType.equals("TEXT") || responseType.equals("TRUE/FALSE") || responseType.equals("MULTIPLE CHOICE"))
            this.responseType = responseType;
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public List<String> getResponseOptions() {
        return responseOptions;
    }

    public void setResponseOptions(List<String> responseOptions) {
        if (responseType.equals("TRUE/FALSE") && responseOptions.size() == 2 ||
                responseType.equals("TEXT") && responseOptions.size() == 0 ||
                responseType.equals("MULTIPLE CHOICE") && responseOptions.size() > 0)
            this.responseOptions = responseOptions;
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
