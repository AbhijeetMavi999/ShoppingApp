package com.admin.repository;

import com.admin.entity.Admin;
import com.admin.exception.AdminNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class AdminRepositoryTests {

    @Autowired
    private AdminRepository adminRepository;

    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = Admin.builder()
                .adminName("Abhijeet Mavi")
                .password("pass@123")
                .build();
    }

    @DisplayName("Unit test for save admin")
    @Test
    public void givenAdmin_whenSaved_thenReturnSavedAdmin() {

        // given - precondition or setup


        // when - action or behaviour that we are going test
        Admin savedAdmin = adminRepository.save(admin);

        // then - verify the output
        Assertions.assertNotNull(admin);
        Assertions.assertTrue(admin.getId() > 0);
        Assertions.assertTrue(admin.getAdminName() == "Abhijeet Mavi");
    }

    @DisplayName("Unit test for get admin by id")
    @Test
    public void givenAdmin_whenFindById_thenReturnAdminObject() {

        adminRepository.save(admin);
        Admin findedAdmin = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new AdminNotFoundException("Admin not found by id: "+admin.getId()));

        // verifying the output
        Assertions.assertNotNull(findedAdmin.getId() > 0);
    }

    @DisplayName("Unit test for update admin")
    @Test
    public void givenAdmin_whenUpdateAdmin_thenReturnAdmin() {

        adminRepository.save(admin);

        Admin findedAdmin = adminRepository.findById(admin.getId())
                .orElseThrow(() -> new AdminNotFoundException("Admin not found by id: "+admin.getId()));

        findedAdmin.setAdminName("Abhi Reddy");
        Admin updatedAdmin = adminRepository.save(findedAdmin);

        Assertions.assertTrue(updatedAdmin.getAdminName().equals("Abhi Reddy"));
    }

    @DisplayName("Unit test for delete admin by id")
    @Test
    public void givenAdminId_whenDeletedById_thenReturnDeletedMessage() {

        Admin admin1 = adminRepository.save(admin);

        adminRepository.deleteById(admin1.getId());

        Optional<Admin> findedAdmin = adminRepository.findById(admin1.getId());

        Assertions.assertTrue(findedAdmin.isEmpty());
    }
}


