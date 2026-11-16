package finder.config;

import finder.BrandAlternativeRepository;
import finder.ClientRepository;
import finder.ClothingAlternativeRepository;
import finder.model.BrandAlternative;
import finder.model.ClothingAlternative;
import finder.model.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(ClientRepository clientRepo,
                                      BrandAlternativeRepository brandRepo,
                                      ClothingAlternativeRepository clothingRepo) {
        return args -> {
            clothingRepo.deleteAll();
            brandRepo.deleteAll();
            clientRepo.deleteAll();

            Client client1 = new Client();
            client1.setName("Client 1");
            client1 = clientRepo.save(client1);

            Client client2 = new Client();
            client2.setName("Client 2");
            client2 = clientRepo.save(client2);

            ClothingAlternative c1 = new ClothingAlternative();
            c1.setClientId(client1.getId());
            c1.setClothingQuery("juoda odine striuke");
            c1.setItemName("Mango Leather Jacket");
            c1.setPriceSegment("50-120");
            c1.setItemLink("https://shop.mango.com/leather-jacket");
            c1.setAesthetics("Biker / Edgy");
            clothingRepo.save(c1);

            ClothingAlternative c2 = new ClothingAlternative();
            c2.setClientId(client1.getId());
            c2.setClothingQuery("juoda odine striuke");
            c2.setItemName("Zara Biker Jacket");
            c2.setPriceSegment("50-120");
            c2.setItemLink("https://zara.com/biker-jacket");
            c2.setAesthetics("Biker / Edgy");
            clothingRepo.save(c2);

            BrandAlternative b1 = new BrandAlternative();
            b1.setClient(client1);
            b1.setTargetBrand("our legacy");
            b1.setAlternativeName("Universal Works");
            b1.setPriceSegment("50-200");
            b1.setAesthetics("Minimalist");
            b1.setPopularityScore(35);
            b1.setBrandLink("https://universalworks.co.uk"); // Nuoroda
            brandRepo.save(b1);

            BrandAlternative b2 = new BrandAlternative();
            b2.setClient(client1);
            b2.setTargetBrand("our legacy");
            b2.setAlternativeName("Selected Homme");
            b2.setPriceSegment("50-200");
            b2.setAesthetics("Minimalist");
            b2.setPopularityScore(50);
            b2.setBrandLink("https://selectedhomme.com"); // Nuoroda
            brandRepo.save(b2);

            ClothingAlternative c2_1 = new ClothingAlternative();
            c2_1.setClientId(client2.getId());
            c2_1.setClothingQuery("vilnonis paltas");
            c2_1.setItemName("COS Wool Overcoat");
            c2_1.setPriceSegment("150-300");
            c2_1.setItemLink("https://cos.com/wool-coat");
            clothingRepo.save(c2_1);

            ClothingAlternative c2_2 = new ClothingAlternative();
            c2_2.setClientId(client2.getId());
            c2_2.setClothingQuery("vilnonis paltas");
            c2_2.setItemName("Arket Classic Coat");
            c2_2.setPriceSegment("150-300");
            c2_2.setItemLink("https://arket.com/classic-coat");
            clothingRepo.save(c2_2);

            BrandAlternative b2_1 = new BrandAlternative();
            b2_1.setClient(client2);
            b2_1.setTargetBrand("acne studios");
            b2_1.setAlternativeName("Norse Projects");
            b2_1.setPriceSegment("100-250");
            b2_1.setAesthetics("Scandi Minimalist");
            b2_1.setPopularityScore(65);
            b2_1.setBrandLink("https://norseprojects.com");
            brandRepo.save(b2_1);

            BrandAlternative b2_2 = new BrandAlternative();
            b2_2.setClient(client2);
            b2_2.setTargetBrand("acne studios");
            b2_2.setAlternativeName("Carhartt WIP");
            b2_2.setPriceSegment("100-250");
            b2_2.setAesthetics("Workwear");
            b2_2.setPopularityScore(88);
            b2_2.setBrandLink("https://carhartt-wip.com");
            brandRepo.save(b2_2);

            BrandAlternative b2_3 = new BrandAlternative();
            b2_3.setClient(client2);
            b2_3.setTargetBrand("our legacy");
            b2_3.setAlternativeName("Beams Plus");
            b2_3.setPriceSegment("50-200");
            b2_3.setAesthetics("Vintage");
            b2_3.setPopularityScore(40);
            b2_3.setBrandLink("https://www.beams.co.jp");
            brandRepo.save(b2_3);
        };
    }
}