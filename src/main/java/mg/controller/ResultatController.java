package mg.controller;

import mg.core.Resultat;
import mg.core.ResultatCandidat;
import mg.repository.BureauVoteRepository;
import mg.repository.CandidatRepository;
import mg.repository.ResultatBVRepository;
import mg.repository.ResultatCandidatBVRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ResultatController {


    private final BureauVoteRepository bureauVoteRepository;
    private final ResultatCandidatBVRepository resultatCandidatBVRepository;
    private final ResultatBVRepository resultatBVRepository;
    private final CandidatRepository candidatRepository;

    public ResultatController(BureauVoteRepository bureauVoteRepository, ResultatCandidatBVRepository resultatCandidatBVRepository, ResultatBVRepository resultatBVRepository, CandidatRepository candidatRepository) {
        this.bureauVoteRepository = bureauVoteRepository;
        this.resultatCandidatBVRepository = resultatCandidatBVRepository;
        this.resultatBVRepository = resultatBVRepository;
        this.candidatRepository = candidatRepository;
    }

    @GetMapping("/resultat")
    public String resultat(Model model) {
        Resultat resultat = new Resultat("%", "%", "%", "%", "%", bureauVoteRepository, resultatBVRepository);
        List<ResultatCandidat> resultatCandidats = resultat.getResultatCandidats(candidatRepository);

        model.addAttribute("resultat", resultat);
        model.addAttribute("resultatCandidats", resultatCandidats);

        return "resultat";
    }
}
