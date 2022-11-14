package net.yorksolutions.mattbauercapstonebe.modules;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    int index;
    String prompt;
    String responseType;
    @ElementCollection
    List<String> responseOptions;
}
