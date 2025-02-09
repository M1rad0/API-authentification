package mg.itu.auth.exceptions;

public class InvalidCodeException extends RuntimeException {
    private final int nbTentativesRestantes;

    public InvalidCodeException(String message, int nbTentativesRestantes) {
        super(message);
        this.nbTentativesRestantes = nbTentativesRestantes;
    }

    public int getNbTentativesRestantes() {
        return nbTentativesRestantes;
    }
}