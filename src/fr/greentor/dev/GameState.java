package fr.greentor.dev;

public enum GameState {
    LAUNCHED("Lancée"),
    LAUNCHING("En lancement"),
    WAITING("En attente"),
    FINISHED("Terminée");

    private final String toString;

    GameState(String toString){
        this.toString = toString;
    }

    public String toString(){
        return this.toString;
    }
}
