package net.yorksolutions.mattbauercapstonebe.modules;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class FinishedProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    String surveyTitle;
    Date dateFinished;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Response> responseList;
}
