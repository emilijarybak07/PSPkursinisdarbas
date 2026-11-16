package finder.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class BrandAlternative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    private String targetBrand;
    private String alternativeName;
    private String priceSegment;
    private String aesthetics;
    private int popularityScore;
    private String brandLink;
}