package com.foodsystem.runner;

import com.foodsystem.entity.Admin;
import com.foodsystem.repo.IAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestRunnner implements CommandLineRunner {


   @Autowired
    IAdminRepo adminRepo;

   BCryptPasswordEncoder encoder=new BCryptPasswordEncoder( 12);
    @Override
    public void run(String... args) throws Exception {

       /* Admin admin=new Admin();
        admin.setAdminName("Sanvii");
        admin.setEmail("sanvii11@gmail.com");
        admin.setPhoneNumber("9894354673");
        admin.setAddress("123 near industrial house");
        admin.setPassword(encoder.encode("sanvii@11"));
        adminRepo.save(admin);*/
    }
}
