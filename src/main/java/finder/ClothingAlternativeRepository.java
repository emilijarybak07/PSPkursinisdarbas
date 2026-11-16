package finder;

import finder.model.ClothingAlternative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClothingAlternativeRepository extends JpaRepository<ClothingAlternative, Long> {
    List<ClothingAlternative> findByClientId(Long clientId);
}