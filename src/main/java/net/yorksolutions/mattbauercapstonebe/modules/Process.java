package net.yorksolutions.mattbauercapstonebe.modules;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    @Column(unique = true)
    public String title;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Stage> questionList;

}
