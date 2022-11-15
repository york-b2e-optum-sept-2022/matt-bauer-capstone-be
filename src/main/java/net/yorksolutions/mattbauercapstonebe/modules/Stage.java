package net.yorksolutions.mattbauercapstonebe.modules;

import javax.persistence.*;
import java.util.List;

@Entity
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public long id;
    public int index;
    public String prompt;
    public String responseType;
    @ElementCollection
    public List<String> responseOptions;
}
