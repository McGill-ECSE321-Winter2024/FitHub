package ca.mcgill.ecse321.sportcenter.dto;

public class AccountRequestDTO {
    private String email;
    private String password;
    private String name;
    private String imageURL;

    public AccountRequestDTO(String email, String password, String name, String imageURL) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
