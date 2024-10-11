package tn.esprit.spring.kaddem.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SubDepartement {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idSubDepart;
    private String nomDepart;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    Departement departement;

}
