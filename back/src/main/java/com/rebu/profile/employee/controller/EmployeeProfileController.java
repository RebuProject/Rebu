package com.rebu.profile.employee.controller;

import com.rebu.profile.employee.service.EmployeeProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles/employees")
public class EmployeeProfileController {

    private final EmployeeProfileService employeeProfileService;

    @PostMapping
    public ResponseEntity<?> generateEmployeeProfile() {
        return null;
    }
}
