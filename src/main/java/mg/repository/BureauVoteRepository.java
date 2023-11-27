package mg.repository;

import mg.entity.BureauVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BureauVoteRepository extends JpaRepository<BureauVote, Integer> {
    @Query("SELECT bv FROM BureauVote bv WHERE bv.code LIKE ?1 OR bv.centreVote LIKE ?2 OR bv.commune LIKE ?3 OR bv.district LIKE ?4 OR bv.region LIKE ?5")
    List<BureauVote> findTri(String code, String centreVote, String commune, String district, String region);
}
