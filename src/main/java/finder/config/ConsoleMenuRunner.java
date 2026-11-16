package finder.config;

import finder.AlternativeSearchService;
import finder.model.BrandAlternative;
import finder.model.ClothingAlternative;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Scanner;

@Configuration
public class ConsoleMenuRunner {

    @Bean
    public CommandLineRunner interactiveMenu(AlternativeSearchService searchService) {
        return args -> {

            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                Long defaultClientId = 1L;

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                boolean running = true;
                while (running) {
                    System.out.println("\n==================================================");
                    System.out.println("   ALTERNATIVE FASHION FINDER - INTERAKTYVUS MENIU");
                    System.out.println("==================================================");
                    System.out.println("1. Paieška pagal drabužio aprašymą / nuotrauką");
                    System.out.println("2. Paieška pagal prekės ženklą (Brand'ą)");
                    System.out.println("3. Pakeisti kliento ID (Dabar ID = " + defaultClientId + ")");
                    System.out.println("0. Išeiti iš programos");
                    System.out.print("Pasirinkite veiksmą (0-3): ");

                    String choice = scanner.nextLine().trim();

                    switch (choice) {
                        case "1":
                            System.out.print("Įveskite drabužio aprašymą (pvz., Juoda odinė striukė): ");
                            String clothingQuery = scanner.nextLine();
                            System.out.print("Įveskite kainų segmentą (pvz., 50-120€): ");
                            String clothingPrice = scanner.nextLine();

                            try {
                                List<ClothingAlternative> results = searchService.searchByClothing(defaultClientId, clothingQuery, clothingPrice);
                                System.out.println("\n--- RASTI PANAŠŪS DRABUŽIAI ---");
                                if (results.isEmpty()) {
                                    System.out.println("Pagal nurodytus kriterijus nieko nerasta.");
                                } else {
                                    for (ClothingAlternative item : results) {
                                        System.out.println(" • " + item.getItemName() + " -> Nuoroda: " + item.getItemLink());
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Klaida: " + e.getMessage());
                            }
                            break;

                        case "2":
                            System.out.print("Įveskite prekės ženklą (pvz., Our Legacy): ");
                            String brandQuery = scanner.nextLine();
                            System.out.print("Įveskite kainų segmentą (pvz., 50-200€): ");
                            String brandPrice = scanner.nextLine();

                            try {
                                List<BrandAlternative> results = searchService.searchByBrand(defaultClientId, brandQuery, brandPrice);
                                System.out.println("\n--- ALTERNATYVŪS BRANDAI IR POPULARUMAS ---");
                                if (results.isEmpty()) {
                                    System.out.println("Alternatyvių brandų nerasta.");
                                } else {
                                    for (BrandAlternative alt : results) {
                                        System.out.println(" • " + alt.getAlternativeName()
                                                + " | Populiarumas: " + alt.getPopularityScore() + "/100"
                                                + " | Nuoroda: " + alt.getBrandLink());
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Klaida: " + e.getMessage());
                            }
                            break;

                        case "3":
                            System.out.print("Įveskite naujo kliento ID (pvz., 1 arba 2): ");
                            try {
                                Long newId = Long.parseLong(scanner.nextLine().trim());
                                defaultClientId = newId;
                                System.out.println("Kliento ID sėkmingai pakeistas į: " + defaultClientId);
                            } catch (NumberFormatException e) {
                                System.out.println("Neteisingas ID formatas!");
                            }
                            break;

                        case "0":
                            System.out.println("Programa baigia darbą. Viso gero!");
                            running = false;
                            System.exit(0);
                            break;

                        default:
                            System.out.println("Neteisingas pasirinkimas. Bandykite dar kartą.");
                    }
                }
            }).start();
        };
    }
}