package finder.controller;

import finder.AlternativeSearchService;
import finder.model.BrandAlternative;
import finder.model.ClothingAlternative;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alternatives")
public class AlternativeSearchController {

    private final AlternativeSearchService searchService;

    public AlternativeSearchController(AlternativeSearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping("/clothing")
    public ResponseEntity<?> searchClothing(
            @RequestParam Long clientId,
            @RequestParam String clothingQuery,
            @RequestParam String priceSegment) {
        try {
            List<ClothingAlternative> results = searchService.searchByClothing(clientId, clothingQuery, priceSegment);
            if (results.isEmpty()) {
                return ResponseEntity.status(404).body("Panašių drabužių nerasta.");
            }
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/brand")
    public ResponseEntity<?> searchBrand(
            @RequestParam Long clientId,
            @RequestParam String targetBrand,
            @RequestParam String priceSegment) {
        try {
            List<BrandAlternative> results = searchService.searchByBrand(clientId, targetBrand, priceSegment);
            if (results.isEmpty()) {
                return ResponseEntity.status(404).body("Alternatyvių brandų nerasta.");
            }
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}