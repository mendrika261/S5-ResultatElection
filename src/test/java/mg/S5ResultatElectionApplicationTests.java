package mg;

import mg.core.ResultatBV;
import mg.core.ResultatCandidatBV;
import mg.service.BureauVoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class S5ResultatElectionApplicationTests {
    @Autowired
    BureauVoteService bureauVoteService;

    @Test
    void contextLoads() throws IOException {
        bureauVoteService.insertResultatBV("src/main/resources/bv2a");
        //ResultatBV.parseFile("src/main/resources/bv2a/110801230104.pdf.txt");
    }

}
