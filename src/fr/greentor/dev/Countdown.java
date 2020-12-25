package fr.greentor.dev;

import org.bukkit.scheduler.BukkitRunnable;

import static fr.greentor.dev.gameManager.gameMessage;

public class Countdown extends BukkitRunnable {

    private int counter;
    private final Game game;

    public Countdown(int counter, Game game) {
        this.game = game;
        if (counter <= 0) {
            throw new IllegalArgumentException("counter must be greater than 0");
        } else {
            this.counter = counter;
        }
    }

    @Override
    public void run() {
        if (counter > 0) {
            gameMessage(this.game, "La partie de " + this.game.getName() + " commence dans " + counter);
            counter--;
        } else {
            gameMessage(this.game, "La partie de " + this.game.getName() + " commence");
            this.cancel();
        }
    }
}