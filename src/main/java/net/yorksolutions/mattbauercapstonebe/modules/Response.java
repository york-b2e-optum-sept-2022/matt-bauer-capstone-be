package net.yorksolutions.mattbauercapstonebe.modules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    String prompt;
    String response;
}
