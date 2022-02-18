package com.springproject.fileparserwebapp.services;

import com.springproject.fileparserwebapp.models.Error;
import com.springproject.fileparserwebapp.models.Role;
import com.springproject.fileparserwebapp.models.User;
import com.springproject.fileparserwebapp.repos.ErrorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorService {
    private final ErrorRepository errorRepository;

    public Error saveError(Error error) {
        return errorRepository.save(error);
    }

    public Error createError(String message) {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new Error((User)currentUser, message);
    }

    public List<Error> getAllErrorsCheckingCurrentUser(User user) {
        List<Error> allErrors = errorRepository.findAll();
        return isCurrentUserManager(user) ? allErrors : allErrors.stream()
                        .filter(e -> e.getUser().getUsername().equals(user.getUsername()))
                        .collect(Collectors.toList());
    }

    private boolean isCurrentUserManager(User user) {
        return user.getRole().equals(Role.MANAGER);
    }
}