package mg.core;

import lombok.Getter;
import lombok.Setter;
import mg.entity.BureauVote;
import mg.repository.BureauVoteRepository;
import mg.repository.CandidatRepository;
import mg.repository.ResultatBVRepository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Resultat {
    List<ResultatBV> resultatsBV = new ArrayList<>();

    public Resultat(String code, String centreVote, String commune, String district, String region,
            BureauVoteRepository bureauVoteRepository, ResultatBVRepository resultatBVRepository) {
        List<String> bureauVotesCodes = bureauVoteRepository.findTri(code, centreVote, commune, district, region).stream().map(BureauVote::getCode).toList();
        resultatsBV.addAll(resultatBVRepository.findByCodeBureauVoteIn(bureauVotesCodes));
    }

    public long getInscrits() {
        return resultatsBV.stream().mapToLong(ResultatBV::getInscrits).sum();
    }

    public long getVotants() {
        return resultatsBV.stream().mapToLong(ResultatBV::getVotants).sum();
    }

    public long getBlancs() {
        return resultatsBV.stream().mapToLong(ResultatBV::getBlancs).sum();
    }

    public long getNuls() {
        return resultatsBV.stream().mapToLong(ResultatBV::getNuls).sum();
    }

    public long getExprimes() {
        return resultatsBV.stream().mapToLong(ResultatBV::getExprimes).sum();
    }

    public double getTauxParticipation() {
        return Math.round((double) getVotants() / getInscrits() * 10000) / 100.0;
    }

    public double getTauxBlancs() {
        return Math.round((double) getBlancs() / getVotants() * 10000) / 100.0;
    }

    public double getTauxNuls() {
        return Math.round((double) getNuls() / getVotants() * 10000) / 100.0;
    }

    public double getTauxExprimes() {
        return Math.round((double) getExprimes() / getVotants() * 10000) / 100.0;
    }

    public List<ResultatCandidat> getResultatCandidats(CandidatRepository candidatRepository) {
        List<ResultatCandidat> resultatCandidats = new ArrayList<>();
        for (ResultatBV resultatBV : getResultatsBV()) {
            for (ResultatCandidat resultatCandidat : resultatBV.getResultatCandidats(candidatRepository)) {
                boolean found = false;
                for (ResultatCandidat rc : resultatCandidats) {
                    if (rc.getCandidat().equals(resultatCandidat.getCandidat())) {
                        rc.setVoix(rc.getVoix() + resultatCandidat.getVoix());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    resultatCandidats.add(resultatCandidat);
                }
            }
        }
        return resultatCandidats;
    }
}
