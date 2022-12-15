package net.yorksolutions.mattbauercapstonebe.modules;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(unique = true)
    private String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Stage> questionList;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
       if(title.length() == 0)
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        this.title = title;
    }

    public Set<Stage> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Set<Stage> questionList) {
        this.questionList = questionList;
    }
}
