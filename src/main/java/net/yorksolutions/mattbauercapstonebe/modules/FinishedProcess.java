package net.yorksolutions.mattbauercapstonebe.modules;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class FinishedProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    public String surveyTitle;
    public Date dateFinished;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Response> responseList;
}
