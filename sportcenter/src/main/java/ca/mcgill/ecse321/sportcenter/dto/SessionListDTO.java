package ca.mcgill.ecse321.sportcenter.dto;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.sportcenter.model.Session;

public class SessionListDTO {

    private List<SessionResponseDTO> sessions;

    public SessionListDTO(List<SessionResponseDTO> sessions) {
        this.sessions = sessions;
    }

    public SessionListDTO() {
    
    }


    public static <T> List<SessionResponseDTO> sessionListToSessionResponseDTOList(List<T> sessions){
        List<SessionResponseDTO> list = new ArrayList<>();
        for (T session : sessions){
            list.add(new SessionResponseDTO((Session)session));
        }
        return list;
    }
   

    public List<SessionResponseDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionResponseDTO> sessions) {
        this.sessions = sessions;
    }
    
}
