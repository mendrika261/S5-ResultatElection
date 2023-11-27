package mg.core;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import mg.repository.CandidatRepository;
import mg.repository.ResultatBVRepository;
import mg.repository.ResultatCandidatBVRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Log
@Entity
@Table(name = "resultatbv")
public class ResultatBV {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    String codeBureauVote;

    @Column(nullable = false)
    long inscrits;

    @Column(nullable = false)
    long blancs;

    @Column(nullable = false)
    long nuls;

    @OneToMany(mappedBy = "resultatBV", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ResultatCandidatBV> resultatCandidatBVList = new ArrayList<>();

    public static String parseVoix(String line) {
        String[] parts = line.split(" ");
        String[] inversedParts = new String[parts.length];
        for(int i=0; i<parts.length; i++) {
            inversedParts[parts.length-1-i] = parts[i];
        }
        StringBuilder nb = new StringBuilder();
        String partiAvecNb = new StringBuilder(inversedParts[1]).reverse().toString();
        for (Character c:partiAvecNb.toCharArray()) {
            if(Character.isDigit(c))
                nb.append(c);
            else break;
        }
        return nb.reverse().toString();
    }

    public static ResultatBV parseFile(String filepath) throws IOException {
        ResultatBV resultatBV = new ResultatBV();
        String content = Utils.readFile(filepath);
        String[] lines = content.split("\n");

        int numeroCandidat = 0;
        for (String line:lines) {
            if(line.startsWith("Bureau de Vote")) {
                resultatBV.setCodeBureauVote(line.split(":")[1].split("-")[0].trim());
            } else if(line.startsWith("Inscrits")) {
                resultatBV.setInscrits(Long.parseLong(line.split(" ")[1].trim()));
            } else if(line.startsWith("Blancs")) {
                String[] parts = line.split(" ");
                resultatBV.setBlancs(Long.parseLong(parts[1].trim()));
                resultatBV.setNuls(Long.parseLong(parts[4].split(":")[1].trim()));
            } else if(line.equals("1")) {
                numeroCandidat += 1;
            }
            if(numeroCandidat!=0) {
                if(line.endsWith("%")) {
                    resultatBV.getResultatCandidatBVList().add(new ResultatCandidatBV(numeroCandidat, parseVoix(line)));
                    numeroCandidat += 1;
                }
            }
        }

        return resultatBV;
    }

    public static void insertResultatBV(String directoryPath, ResultatBVRepository resultatBVRepository, ResultatCandidatBVRepository resultatCandidatBVRepository) {
        String[] files = Utils.listFiles(directoryPath);
        int nb = 0;
        for (String file:files) {
            try {
                ResultatBV resultatBV = parseFile(file);
                System.out.println(resultatBV.getResultatCandidatBVList().size());
                resultatBV.getResultatCandidatBVList().forEach(resultatCandidatBV -> {
                    resultatCandidatBV.setResultatBV(resultatBV);
                });
                resultatBVRepository.save(resultatBV);
                System.out.println(nb + " " + resultatBV.getCodeBureauVote());
                nb += 1;
            } catch (IOException | NumberFormatException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier " + file + " : " + e.getMessage());
            }
        }
    }

    public long getVotants() {
        return getInscrits() - getBlancs() - getNuls();
    }

    public long getExprimes() {
        return getResultatCandidatBVList().stream().mapToLong(ResultatCandidatBV::getVoix).sum();
    }

    public ResultatCandidat[] getResultatCandidats(CandidatRepository candidatRepository) {
        ResultatCandidat[] resultatCandidats = new ResultatCandidat[getResultatCandidatBVList().size()];
        for (ResultatCandidatBV resultatCandidatBV : getResultatCandidatBVList()) {
            resultatCandidats[resultatCandidatBV.getNumeroCandidat()-1] = new ResultatCandidat(resultatCandidatBV.getNumeroCandidat(), resultatCandidatBV.getVoix(), candidatRepository);
        }
        return resultatCandidats;
    }
}
