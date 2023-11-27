package mg.core;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "resultat_candidatbv")
public class ResultatCandidatBV {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    int numeroCandidat;

    @Column(nullable = false)
    long voix;

    @ManyToOne
    @JoinColumn(name = "resultat_bv_id")
    private ResultatBV resultatBV;

    public ResultatCandidatBV(int numeroCandidat, long voix) {
        setNumeroCandidat(numeroCandidat);
        setVoix(voix);
    }

    public ResultatCandidatBV(int numeroCandidat, String nb) {
        this(numeroCandidat, Long.parseLong(nb));
    }
}
