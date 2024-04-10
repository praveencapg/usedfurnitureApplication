package com.admin.serviceimplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.adminDTO.AdminDTO;
import com.admin.entity.Admin;
import com.admin.exception.AdminNotFoundException;
import com.admin.repository.AdminRepository;
import com.admin.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Method to add a new admin
    @Override
    public AdminDTO addAdmin(Admin admin) {
        // Create a new AdminDTO object
        AdminDTO adminDTO = new AdminDTO();
        
        // Save the admin entity using the repository
        adminRepository.save(admin);

        // Set attributes of adminDTO from the saved admin entity
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setAddress(admin.getAddress());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setNumber(admin.getNumber());
        adminDTO.setId(admin.getId());

        return adminDTO;
    }

    // Method to update an existing admin
    @Override
    public String updateAdmin(int id, AdminDTO adminDTO) {
        try {
            // Find the admin by ID
            Admin admin = adminRepository.findById(id).orElseThrow(() -> new AdminNotFoundException("Admin Not Found"));

            // Update admin attributes if not null in the DTO
            if (adminDTO.getUsername() != null)
                admin.setUsername(adminDTO.getUsername());
            if (adminDTO.getNumber() != 0)
                admin.setNumber(adminDTO.getNumber());
            if (adminDTO.getAddress() != null)
                admin.setAddress(adminDTO.getAddress());
            if (adminDTO.getEmail() != null)
                admin.setEmail(adminDTO.getEmail());
            if (adminDTO.getPassword() != null)
                admin.setPassword(adminDTO.getPassword());

            // Save the updated admin entity
            adminRepository.save(admin);
        } catch (AdminNotFoundException e) {
            // Handle exception if admin not found
            return "Admin data not updated";
        }
        return "Admin Updated Successfully";
    }

    // Method to delete an admin by ID
    @Override
    public String deleteAdmin(int id) throws AdminNotFoundException {
        // Find admin by ID
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new AdminNotFoundException("No admin found for given id...."));

        // Delete the admin entity
        adminRepository.delete(admin);
        return "Admin deleted successfully!!";
    }

    // Method to get an admin by email
    @Override
    public AdminDTO getAdminByEmail(String email) throws AdminNotFoundException {
        // Find admin by email
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) {
            throw new AdminNotFoundException("Admin not found for given email....");
        }

        // Create AdminDTO object and set attributes from the found admin entity
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setAddress(admin.getAddress());
        adminDTO.setEmail(admin.getEmail());
        adminDTO.setNumber(admin.getNumber());
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setId(admin.getId());

        return adminDTO;
    }

    // Method to read all admins
    @Override
    public List<AdminDTO> readAllAdmins() throws AdminNotFoundException {
        // Retrieve all admins using the repository
        List<Admin> admins = adminRepository.findAll();

        // Throw exception if no admins found
        if (admins.isEmpty()) {
            throw new AdminNotFoundException("Admins Not Registered");
        }

        // Create a list to hold AdminDTO objects
        List<AdminDTO> adminDTOs = new ArrayList<>();
        for (Admin admin : admins) {
            // Create AdminDTO object for each admin entity and set attributes
            AdminDTO adminDTO = new AdminDTO();
            adminDTO.setUsername(admin.getUsername());
            adminDTO.setAddress(admin.getAddress());
            adminDTO.setEmail(admin.getEmail());
            adminDTO.setPassword(admin.getPassword());
            adminDTO.setNumber(admin.getNumber());
            adminDTO.setId(admin.getId());
            adminDTOs.add(adminDTO);
        }
        return adminDTOs;
    }
}
