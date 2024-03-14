package ca.mcgill.ecse321.sportcenter.dto;

import java.util.List;

public class SessionListDTO {

    private List<SessionResponseDTO> sessions;

    public SessionListDTO(List<SessionResponseDTO> sessions) {
        this.sessions = sessions;
    }

    public List<SessionResponseDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionResponseDTO> sessions) {
        this.sessions = sessions;
    }
    
}
