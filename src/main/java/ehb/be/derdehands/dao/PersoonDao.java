package ehb.be.derdehands.dao;

import ehb.be.derdehands.entiteit.Persoon;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersoonDao extends CrudRepository<Persoon, Integer> {

    Optional<Persoon> findByEmail(String email);
}
