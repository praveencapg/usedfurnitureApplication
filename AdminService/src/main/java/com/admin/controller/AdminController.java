package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.adminDTO.AdminDTO;
import com.admin.entity.Admin;
import com.admin.exception.AdminNotFoundException;
import com.admin.service.AdminService;

@RestController
@RequestMapping("/admin")
//@CrossOrigin(origins = "http://localhost:3000") // front-end connection
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;
    

    //  to register a new admin
    @PostMapping("/registerAdmin")
    public AdminDTO addAdmin(@RequestBody Admin admin) {
        // Calls the service layer to add a new admin and returns the DTO representation of the added admin
        return adminService.addAdmin(admin);
    }
    

    //  to update an existing admin
    @PutMapping("/updateAdmin/{admin_id}")
    public String updateAdmin(@PathVariable(value = "admin_id") int id, @RequestBody AdminDTO adminDTO) {
        // Calls the service layer to update the admin with the given ID using the provided DTO
        return adminService.updateAdmin(id, adminDTO);
    }
    

    //  to retrieve an admin by email
    @GetMapping("/adminByEmail/{admin_email}")
    public AdminDTO getAdminByEmail(@PathVariable(value = "admin_email") String email) throws AdminNotFoundException {
        // Calls the service layer to retrieve an admin by email
        return adminService.getAdminByEmail(email);
    }

    //  to retrieve all admins
    @GetMapping("/allAdmins")
    public List<AdminDTO> getAllAdmins() throws AdminNotFoundException {
        // Calls the service layer to retrieve all admins
        return adminService.readAllAdmins();
    }

    //  to delete an admin by ID
    @DeleteMapping("/deleteadmin/{admin_id}")
    public String deleteAdmin(@PathVariable("admin_id") int id) throws AdminNotFoundException {
        // Calls the service layer to delete an admin by ID
        return adminService.deleteAdmin(id);
    }
}
