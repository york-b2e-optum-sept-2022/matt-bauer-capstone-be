package net.yorksolutions.mattbauercapstonebe.modules;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @Column(unique = true)
    String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Stage> questionList;

}
