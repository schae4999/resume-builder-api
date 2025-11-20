package in.joyhong.resumebuilderapi.controller;

import in.joyhong.resumebuilderapi.document.User;
import in.joyhong.resumebuilderapi.dto.AuthResponse;
import in.joyhong.resumebuilderapi.dto.LoginRequest;
import in.joyhong.resumebuilderapi.dto.RegisterRequest;
import in.joyhong.resumebuilderapi.service.AuthService;
import in.joyhong.resumebuilderapi.service.FileUploadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static in.joyhong.resumebuilderapi.util.AppConstants.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(AUTH_CONTROLLER)
public class AuthController {

    private final AuthService authService;
    private final FileUploadService fileUploadService;

    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Inside AuthController - register(): {}", request);
        AuthResponse response = authService.register(request);
        log.info("Response from service: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
    }

    @GetMapping(VERIFY_EMAIL)
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        log.info("Inside AuthController - verifyEmail(): {}", token);
        authService.verifyEmail(token);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Email verified successfully"));
    }

    @PostMapping(UPLOAD_PROFILE)
    public ResponseEntity<?> uploadImage(@RequestPart("image") MultipartFile file) throws IOException {
        log.info("Inside AuthController - uploadImage()");
        Map<String, String> response = fileUploadService.uploadSingleImage(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(RESEND_VERIFICATION)
    public ResponseEntity<?> resendVerification(@RequestBody Map<String, String> body) {
        // Get the email from request
        String email = body.get("email");

        // Add the validations
        if (Objects.isNull(email)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required."));
        }

        // Call the service method to resend verification link
        authService.resendVerification(email);
        
        // Return response
        return ResponseEntity.ok(Map.of("success", true, "message", "Verification email sent."));
    }

    @GetMapping(PROFILE)
    public ResponseEntity<?> getProfile(Authentication authentication) {
        // Get the principal object
        Object principalObject = authentication.getPrincipal();

        // Call the service method
        AuthResponse currentProfile = authService.getProfile(principalObject);

        // Return the response
        return ResponseEntity.ok(currentProfile);
    }

}
