package mg.itu.auth.exceptions.responses;

public class AttemptsResponse extends ApiErrorResponse {    
    private int nbTentativesRestantes;

    public int getNbTentativesRestantes() {
        return nbTentativesRestantes;
    }

    public void setNbTentativesRestantes(int nbTentativesRestantes) {
        this.nbTentativesRestantes = nbTentativesRestantes;
    }

    public AttemptsResponse(int httpCode, int errorCode, String message, int nbTentativesRestantes) {
        super(httpCode, errorCode, message);
        this.nbTentativesRestantes=nbTentativesRestantes;
    }
}
