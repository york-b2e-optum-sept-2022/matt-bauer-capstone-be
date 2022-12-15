package net.yorksolutions.mattbauercapstonebe.modules;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class FinishedProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String surveyTitle;
    private Date dateFinished;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Response> responseList;

    public long getId() {
        return id;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        if(surveyTitle.length() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.surveyTitle = surveyTitle;
    }

    public Date getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(Date dateFinished) {
        this.dateFinished = dateFinished;
    }

    public Set<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(Set<Response> responseList) {
        if(responseList.size() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.responseList = responseList;
    }
}
