import finder.AlternativeSearchService;
import finder.BrandAlternativeRepository;
import finder.ClientRepository;
import finder.model.BrandAlternative;
import finder.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = finder.AlternativeFinderApplication.class)
public class AlternativeSearchServiceTest {

    @Autowired
    private AlternativeSearchService searchService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BrandAlternativeRepository alternativeRepository;

    private Client clientA;
    private Client clientB;

    @BeforeEach
    void setUp() {
        alternativeRepository.deleteAll();
        clientRepository.deleteAll();

        clientA = new Client();
        clientA.setName("Client A");
        clientA = clientRepository.save(clientA);

        clientB = new Client();
        clientB.setName("Client B");
        clientB = clientRepository.save(clientB);


        BrandAlternative altA = new BrandAlternative();
        altA.setClient(clientA);
        altA.setTargetBrand("Our Legacy");
        altA.setAlternativeName("Universal Works");
        altA.setPriceSegment("50-200€");
        altA.setAesthetics("Minimalist");
        altA.setPopularityScore(35);
        alternativeRepository.save(altA);


        BrandAlternative altB = new BrandAlternative();
        altB.setClient(clientB);
        altB.setTargetBrand("Our Legacy");
        altB.setAlternativeName("Beams Plus");
        altB.setPriceSegment("50-200€");
        altB.setAesthetics("Minimalist");
        altB.setPopularityScore(40);
        alternativeRepository.save(altB);
    }

    // 1. Įprastas atvejis (Sėkminga prekės ženklo alternatyvų paieška)
    @Test
    void testNormalSearchSuccess() {
        List<BrandAlternative> results = searchService.searchByBrand(clientA.getId(), "Our Legacy", "50-200€");
        assertEquals(1, results.size());
        assertEquals("Universal Works", results.get(0).getAlternativeName());
    }

    // 2. Ribinis / Konfliktinis atvejis (Kai alternatyvų nerasta)
    @Test
    void testConflictNoAlternativesFound() {
        List<BrandAlternative> results = searchService.searchByBrand(clientA.getId(), "Our Legacy", "500-1000€");
        assertTrue(results.isEmpty());
    }

    // 3. Klaidos atvejis (Neįvesti privalomi duomenys)
    @Test
    void testErrorMissingInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            searchService.searchByBrand(clientA.getId(), "", "50-200€");
        });
    }

    @Test
    void testClientDataIsolation() {

        List<BrandAlternative> resultsA = searchService.searchByBrand(clientA.getId(), "Our Legacy", "50-200€");


        assertEquals(1, resultsA.size());
        assertEquals("Universal Works", resultsA.get(0).getAlternativeName());
        assertNotEquals("Beams Plus", resultsA.get(0).getAlternativeName());
    }
}