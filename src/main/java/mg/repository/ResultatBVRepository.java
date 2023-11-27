package mg.repository;

import mg.core.ResultatBV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultatBVRepository extends JpaRepository<ResultatBV, Long> {
    List<ResultatBV> findByCodeBureauVoteIn(List<String> codesBureauVote);
}