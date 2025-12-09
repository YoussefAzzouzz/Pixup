package com.pixup.backend.config;

import com.pixup.backend.entity.Game;
import com.pixup.backend.repository.GameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

        @Bean
        CommandLineRunner initDatabase(GameRepository repository) {
                return args -> {
                        createGameIfNotFound(repository, "The Legend of Zelda: Breath of the Wild",
                                        "Step into a world of discovery, exploration, and adventure in The Legend of Zelda: Breath of the Wild.",
                                        new BigDecimal("59.99"),
                                        "https://images.igdb.com/igdb/image/upload/t_cover_big/co3p2d.png",
                                        "Nintendo Switch", "Action-Adventure");

                        createGameIfNotFound(repository, "Elden Ring",
                                        "THE NEW FANTASY ACTION RPG. Rise, Tarnished, and be guided by grace to brandish the power of the Elden Ring.",
                                        new BigDecimal("59.99"),
                                        "https://images.igdb.com/igdb/image/upload/t_cover_big/co4jni.png",
                                        "PC, PS5, Xbox", "RPG");

                        createGameIfNotFound(repository, "God of War Ragnarök",
                                        "The freezing winds of Fimbulwinter have come to Midgard, making survival for Kratos, Atreus, and Mimir more challenging than ever before.",
                                        new BigDecimal("69.99"),
                                        "https://images.igdb.com/igdb/image/upload/t_cover_big/co5s5v.png", "PS5",
                                        "Action-Adventure");

                        createGameIfNotFound(repository, "Cyberpunk 2077",
                                        "Cyberpunk 2077 is an open-world, action-adventure RPG set in the dark future of Night City — a dangerous megalopolis obsessed with power, glamor, and ceaseless body modification.",
                                        new BigDecimal("59.99"),
                                        "https://images.igdb.com/igdb/image/upload/t_cover_big/co650i.png",
                                        "PC, PS5, Xbox", "RPG");

                        createGameIfNotFound(repository, "Hollow Knight",
                                        "Forge your own path in Hollow Knight! An epic action adventure through a vast ruined kingdom of insects and heroes.",
                                        new BigDecimal("14.99"),
                                        "https://images.igdb.com/igdb/image/upload/t_cover_big/co74b0.png",
                                        "PC, Switch", "Metroidvania");

                        createGameIfNotFound(repository, "Red Dead Redemption 2",
                                        "Winner of over 175 Game of the Year Awards and recipient of over 250 perfect scores, RDR2 is the epic tale of outlaw Arthur Morgan and the infamous Van der Linde gang.",
                                        new BigDecimal("59.99"),
                                        "https://images.igdb.com/igdb/image/upload/t_cover_big/co1q1f.png",
                                        "PC, PS5, Xbox", "Action-Adventure");
                };
        }

        private void createGameIfNotFound(GameRepository repository, String title, String description, BigDecimal price,
                        String imageUrl, String platform, String genre) {
                // Since we don't have findByTitle exposed yet, let's just use findAll and
                // filter, or assume we can add it safely if we add the method.
                // Better: Add findByTitle to Repository first.
                // Wait, I can't do that in this tool call easily.
                // I will use Example matcher or just check if any game matches.
                // Actually, for simplicity in this MVP, I will check if count < 6.
                // But that's risky if I delete one.
                // I'll add the method to the repository in the next step. For now I'll just
                // write this code assuming findByTitle exists, and then add it.
                // OR I can use a simple check:
                boolean exists = repository.findAll().stream().anyMatch(g -> g.getTitle().equals(title));
                if (!exists) {
                        Game game = new Game();
                        game.setTitle(title);
                        game.setDescription(description);
                        game.setPrice(price);
                        game.setImageUrl(imageUrl);
                        game.setPlatform(platform);
                        game.setGenre(genre);
                        repository.save(game);
                }
        }
}
