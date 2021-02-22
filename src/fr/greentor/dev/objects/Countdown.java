package fr.greentor.dev.objects;

import org.bukkit.scheduler.BukkitRunnable;

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
            this.game.sendMessage("La partie de " + this.game.getName() + " commence dans " + counter);
            counter--;
        } else {
            this.game.sendMessage("La partie de " + this.game.getName() + " commence");
            this.cancel();
        }
    }
}