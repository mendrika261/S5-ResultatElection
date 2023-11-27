package mg.core;

import lombok.Getter;
import lombok.Setter;
import mg.entity.Candidat;
import mg.repository.CandidatRepository;

@Getter
@Setter
public class ResultatCandidat {
    Candidat candidat;
    long voix;

    public ResultatCandidat(int numeroCandidat, long voix, CandidatRepository candidatRepository) {
        setCandidat(candidatRepository.findByNumero(numeroCandidat));
    }

    public double getTaux(long totalVoix) {
        return Math.round((double) voix / totalVoix * 10000) / 100.0;
    }
}
