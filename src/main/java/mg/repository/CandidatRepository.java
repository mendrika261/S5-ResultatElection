package mg.repository;

import mg.entity.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatRepository extends JpaRepository<Candidat, Integer> {
    Candidat findByNumero(int numeroCandidat);
}