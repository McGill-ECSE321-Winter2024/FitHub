package ca.mcgill.ecse321.sportcenter.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.SessionPackage;

public class SessionPackageListDTO {

    private List<SessionPackageResponseDTO> sessionPackages;

    public SessionPackageListDTO(List<SessionPackageResponseDTO> sessionPackages) {
        this.sessionPackages = sessionPackages;
    }

    public SessionPackageListDTO(){

    }


    public static <T> List<SessionPackageResponseDTO> sessionPackageListToSessionPackageResponseDTOList(List<T> sessionPackages){
        List<SessionPackageResponseDTO> list = new ArrayList<>();
        for (T sessionPackage : sessionPackages){
            list.add(new SessionPackageResponseDTO((SessionPackage)sessionPackage));
        }
        return list;
    }
   

    public List<SessionPackageResponseDTO> getSessionPackages() {
        return sessionPackages;
    }

    public void setSessions(List<SessionPackageResponseDTO> sessionPackages) {
        this.sessionPackages = sessionPackages;
    }

    @Override
    public String toString() {
        return "Size of SessionPackageList: " + this.sessionPackages.size();
    }
    
}
