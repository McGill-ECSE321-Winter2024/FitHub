package ca.mcgill.ecse321.sportcenter.dto;

public class LoginResponseDTO {
    String verified;

    public LoginResponseDTO(boolean verified){
        if (verified) {
            this.verified = "Successful";
        }
        else {
            this.verified = "Failed";
        }
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(boolean verified){
        if (verified) {
            this.verified = "Successful";
        }
        else {
            this.verified = "Failed";
        }
    }
}
