package com.emse.spring.faircop.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class SecurityController {
    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<String> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUsername());
    }

    @Secured("ROLE_ADMIN") // (1)
    @GetMapping(path = "/{id}")
    public String findUserName() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return authentication.getName();
    }


}