package finder;

import finder.model.BrandAlternative;
import finder.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandAlternativeRepository extends JpaRepository<BrandAlternative, Long> {
    List<BrandAlternative> findByClient(Client client);
}