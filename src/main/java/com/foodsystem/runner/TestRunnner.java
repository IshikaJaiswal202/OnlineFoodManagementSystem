package com.foodsystem.runner;

import com.foodsystem.entity.Admin;
import com.foodsystem.repo.IAdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunnner implements CommandLineRunner {

    @Autowired
    IAdminRepo adminRepo;

    @Override
    public void run(String... args) throws Exception {
        Admin admin=new Admin(
                "Sanvii",
                "sanvii@11",
                "sanvii11@gmail.com",
                "1234567890",
                "123 near industrial house"
        );
          adminRepo.save(admin);
    }
}
