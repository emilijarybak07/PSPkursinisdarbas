package finder.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ClothingAlternative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clientId;
    private String clothingQuery;
    private String itemName;
    private String priceSegment;
    private String itemLink;
    private String aesthetics;
}