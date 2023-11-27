package mg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bureau_vote")
public class BureauVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "commune", nullable = false)
    private String commune;

    @Column(name = "fokontany", nullable = false)
    private String fokontany;

    @Column(name = "centre_vote", nullable = false)
    private String centreVote;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "nom", nullable = false)
    private String nom;
}