package com.pixup.backend.controller;

import com.pixup.backend.entity.User;
import com.pixup.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final Path fileStorageLocation;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam String username) {
        System.out.println("Getting profile for: " + username);
        return userRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestParam String username, @RequestBody User updatedUser) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    user.setBio(updatedUser.getBio());
                    // Don't update password/username here for safety in this simple impl
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/profile/picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file,
            @RequestParam String username) {
        // Simple security check: In real app, verify authenticated user matches
        // 'username'

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Filename contains invalid path sequence " + fileName);
            }

            String newFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path targetLocation = this.fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "http://localhost:8080/uploads/" + newFileName;

            return userRepository.findByUsername(username)
                    .map(user -> {
                        user.setProfilePictureUrl(fileUrl);
                        return ResponseEntity.ok(userRepository.save(user));
                    })
                    .orElse(ResponseEntity.notFound().build());

        } catch (IOException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Could not store file " + fileName + ". Please try again!"));
        }
    }
}
