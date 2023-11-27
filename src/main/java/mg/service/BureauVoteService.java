package mg.service;

import lombok.Getter;
import mg.core.ResultatBV;
import mg.core.Utils;
import mg.repository.BureauVoteRepository;
import mg.repository.ResultatBVRepository;
import mg.repository.ResultatCandidatBVRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Getter
public class BureauVoteService {
    static final String BV_PATH = "src/main/resources/bv.csv";
    private final BureauVoteRepository bureauVoteRepository;
    private final ResultatCandidatBVRepository resultatCandidatBVRepository;
    private final ResultatBVRepository resultatBVRepository;

    public BureauVoteService(BureauVoteRepository bureauVoteRepository, ResultatCandidatBVRepository resultatCandidatBVRepository, ResultatBVRepository resultatBVRepository) {
        this.bureauVoteRepository = bureauVoteRepository;
        this.resultatCandidatBVRepository = resultatCandidatBVRepository;
        this.resultatBVRepository = resultatBVRepository;
    }

    @Transactional
    public void saveFrom(String path) throws IOException {
        String[][] data = Utils.readCsv(path);
        for (String[] row : data) {
            String id = row[0];
            String region = row[1];
            String district = row[2];
            String commune = row[3];
            String fokontany = row[4];
            String centreVote = row[5];
            String code = row[6];
            String nom = row[7];
            mg.entity.BureauVote bureauVote = new mg.entity.BureauVote();
            bureauVote.setId(Integer.parseInt(id));
            bureauVote.setRegion(region);
            bureauVote.setDistrict(district);
            bureauVote.setCommune(commune);
            bureauVote.setFokontany(fokontany);
            bureauVote.setCentreVote(centreVote);
            bureauVote.setCode(code);
            bureauVote.setNom(nom);
            bureauVoteRepository.save(bureauVote);
        }
    }

    @Transactional
    public void insertResultatBV(String directoryPath) {
        ResultatBV.insertResultatBV(directoryPath, resultatBVRepository, resultatCandidatBVRepository);
    }
}
