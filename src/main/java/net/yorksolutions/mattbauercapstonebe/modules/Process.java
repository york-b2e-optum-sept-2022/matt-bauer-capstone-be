package net.yorksolutions.mattbauercapstonebe.modules;

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

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Stage> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(Set<Stage> questionList) {
        this.questionList = questionList;
    }
}
