package com.admin.service;

import com.admin.entity.Admin;
import com.admin.exception.AdminNotFoundException;

public interface AdminService {

    public Admin saveAdmin(Admin admin);

    public Admin getAdminById(Integer id);

    public Admin updateAdmin(Integer id, Admin admin);

    public void deleteAdminById(Integer id);
}
