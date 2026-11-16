package finder;

import finder.model.BrandAlternative;
import finder.model.Client;
import finder.model.ClothingAlternative;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlternativeSearchService {

    private final BrandAlternativeRepository brandRepo;
    private final ClothingAlternativeRepository clothingRepo;
    private final ClientRepository clientRepository;

    public AlternativeSearchService(BrandAlternativeRepository brandRepo,
                                    ClothingAlternativeRepository clothingRepo,
                                    ClientRepository clientRepository) {
        this.brandRepo = brandRepo;
        this.clothingRepo = clothingRepo;
        this.clientRepository = clientRepository;
    }
    
    private String normalizeText(String text) {
        if (text == null) return "";
        String result = text.toLowerCase().trim();
        result = Normalizer.normalize(result, Normalizer.Form.NFD);
        result = result.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        result = result.replaceAll("\\s+", " ");
        return result;
    }

    private String normalizePrice(String price) {
        if (price == null) return "";
        String result = price.toLowerCase();
        result = result.replace("€", "").replaceAll("eur", "").replaceAll("\\s+", "");
        return result;
    }


    public List<ClothingAlternative> searchByClothing(Long clientId, String clothingQuery, String priceSegment) {
        if (clothingQuery == null || clothingQuery.trim().isEmpty() || priceSegment == null || priceSegment.trim().isEmpty()) {
            throw new IllegalArgumentException("Būtina nurodyti drabužio aprašymą ir kainų segmentą!");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Klientas nerastas."));

        String normQuery = normalizeText(clothingQuery);
        String normPrice = normalizePrice(priceSegment);

        List<ClothingAlternative> allItems = clothingRepo.findByClientId(clientId);

        return allItems.stream()
                .filter(item -> normalizeText(item.getClothingQuery()).equals(normQuery))
                .filter(item -> normalizePrice(item.getPriceSegment()).equals(normPrice))
                .collect(Collectors.toList());
    }


    public List<BrandAlternative> searchByBrand(Long clientId, String targetBrand, String priceSegment) {
        if (targetBrand == null || targetBrand.trim().isEmpty() || priceSegment == null || priceSegment.trim().isEmpty()) {
            throw new IllegalArgumentException("Būtina nurodyti prekės ženklą ir kainų segmentą!");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Klientas nerastas."));

        String normTarget = normalizeText(targetBrand);
        String normPrice = normalizePrice(priceSegment);

        List<BrandAlternative> allBrands = brandRepo.findByClient(client);

        String detectedAesthetics = allBrands.stream()
                .filter(alt -> normalizeText(alt.getTargetBrand()).equals(normTarget))
                .map(BrandAlternative::getAesthetics)
                .findFirst()
                .orElse("");


        return allBrands.stream()
                .filter(alt -> normalizePrice(alt.getPriceSegment()).equals(normPrice))
                .filter(alt -> {
                    if (!detectedAesthetics.isEmpty()) {

                        return normalizeText(alt.getAesthetics()).equals(normalizeText(detectedAesthetics));
                    }
                    return true;
                })
                .filter(alt -> !normalizeText(alt.getAlternativeName()).equals(normTarget))
                .sorted((a, b) -> Integer.compare(b.getPopularityScore(), a.getPopularityScore()))
                .collect(Collectors.toList());
    }
}