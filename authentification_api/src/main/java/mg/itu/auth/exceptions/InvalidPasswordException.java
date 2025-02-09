package mg.itu.auth.exceptions;

public class InvalidPasswordException extends RuntimeException {
    private int nbTentativesRestantes;
    public InvalidPasswordException(String message,int nbTentativesRestantes) {
        super(message);
        this.nbTentativesRestantes=nbTentativesRestantes;
    }

    public void setNbTentativesRestantes(int nbTentativesRestantes) {
        this.nbTentativesRestantes = nbTentativesRestantes;
    }

    public int getNbTentativesRestantes() {
        return nbTentativesRestantes;
    }
}