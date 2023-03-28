package com.admin.service.impl;

import com.admin.entity.Admin;
import com.admin.exception.AdminAlreadyExistException;
import com.admin.exception.AdminNotFoundException;
import com.admin.repository.AdminRepository;
import com.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin saveAdmin(Admin admin) {
        log.info("saveAdmin() method of AdminServiceImpl is called");
        Admin findAdmin = adminRepository.findByAdminName(admin.getAdminName());
        if(findAdmin != null) {
            log.warn("Admin already exist by name: "+admin.getAdminName());
            throw new AdminAlreadyExistException("Admin already exist by name: "+admin.getAdminName());
        }
        Admin savedAdmin = adminRepository.save(admin);
        return savedAdmin;
    }

    @Cacheable(cacheNames = "admin", key = "#id")
    @Override
    public Admin getAdminById(Integer id) {
        log.info("getAdminById() method of AdminServiceImpl is called");
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("admin not found by id: "+id));
        return admin;
    }

    @CachePut(cacheNames = "admin", key = "#id")
    @Override
    public Admin updateAdmin(Integer id, Admin admin) {
        log.info("updateAdmin() method of AdminServiceImpl is called");
        Admin update = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("admin not found by id: "+id));

        update.setAdminName(admin.getAdminName());
        update.setPassword(admin.getPassword());

        Admin updatedAdmin = adminRepository.save(update);
        return updatedAdmin;
    }

    @CacheEvict(cacheNames = "admin", key = "#id")
    @Override
    public void deleteAdminById(Integer id) {
        log.info("deleteAdminById() method of AdminServiceImpl is called");
        adminRepository.findById(id).orElseThrow(() -> new AdminNotFoundException("admin not found by id: "+id));
        adminRepository.deleteById(id);
    }
}
