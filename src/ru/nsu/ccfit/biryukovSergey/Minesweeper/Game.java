package ru.nsu.ccfit.biryukovSergey.Minesweeper;

import ru.nsu.ccfit.biryukovSergey.Minesweeper.Exceptions.InvalidSettings;

public class Game {
    private final GameSettings settings;
    public Game(GameSettings settings) {
        this.settings = settings;
    }
    private boolean CheckSettings() {
        return !(this.settings.width < 0 || this.settings.height < 0 || this.settings.mineCount < 0 || this.settings.mineCount > this.settings.width * this.settings.height);
    }
    public void Start() throws InvalidSettings {
        if(!CheckSettings()) throw new InvalidSettings("Invalid game settings!");
        GameController controller = new GameController(settings);
        controller.Start();
    }
}
