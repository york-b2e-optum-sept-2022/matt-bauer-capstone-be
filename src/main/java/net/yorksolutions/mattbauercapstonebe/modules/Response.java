package net.yorksolutions.mattbauercapstonebe.modules;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private int index;

    private String prompt;
    private String response;

    private String responseType;

    public long getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        if (prompt.length() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.prompt = prompt;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        if (response.length() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.response = response;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        if (responseType.equals("TEXT") || responseType.equals("TRUE/FALSE") || responseType.equals("MULTIPLE CHOICE"))
            this.responseType = responseType;
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        if (index < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.index = index;
    }
}
