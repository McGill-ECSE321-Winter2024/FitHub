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
            accountService.findOwnerByEmail("@"); // If it throws an error here then create one
            System.out.println("Default account @ is already created");
        } catch (Exception e) {
            System.out.println("Created default owner account with email: \"@\" and password: \"password\"");
            accountService.createOwnerAccount("@", "password", "Admin", "");
        }

        // Add more initialization logic as needed
    }
}