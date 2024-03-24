package ca.mcgill.ecse321.sportcenter;

import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ca.mcgill.ecse321.sportcenter.service.AccountService;
import ca.mcgill.ecse321.sportcenter.service.SportCenterManagementService;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private SportCenterManagementService sportCenterService;

    @Override
    public void run(String... args) throws Exception {
        initializeDatabase();
    }

    private void initializeDatabase() {
        // If the unique sportCenter is not there, create it
        if (sportCenterService.getAllSportCenters().isEmpty()) {
            Time openingTime = Time.valueOf("6:0:0");
            Time closingTime = Time.valueOf("23:59:0");
            sportCenterService.createSportCenter("Fithub", openingTime, closingTime, "16", "sportcenter@mail.com", "455-645-4566");
        }

        // Create a default owner Admin account if it isnt already in the database
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