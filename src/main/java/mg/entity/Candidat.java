package mg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "candidat")
public class Candidat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date_election")
    private LocalDate dateElection;

    @Column(name = "numero", nullable = false)
    private int numero;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "parti", nullable = false)
    private String parti;
}