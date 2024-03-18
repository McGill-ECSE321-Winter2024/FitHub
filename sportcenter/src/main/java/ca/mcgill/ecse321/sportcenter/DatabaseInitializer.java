package ca.mcgill.ecse321.sportcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ca.mcgill.ecse321.sportcenter.service.AccountService;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        initializeDatabase();
    }

    private void initializeDatabase() {
        // Create a default if it isnt already in the database
        try {
            accountService.findOwnerByEmail("@");
            accountService.createOwnerAccount("@", "password", "Admin", "");
        } catch (Exception e) {
            System.out.println("Default account @ is already created");
        }

        // Add more initialization logic as needed
    }
}