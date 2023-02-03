package ru.nsu.ccfit.biryukovSergey.Minesweeper;

import ru.nsu.ccfit.biryukovSergey.Minesweeper.Exceptions.InvalidSettings;

enum ViewMode {
    CONSOLE_VIEW,
    GUI_VIEW
}
class GameSettings {
    public final ViewMode mode;
    public final int width, height, mineCount;
    public GameSettings(ViewMode mode, int width, int height, int mineCount) {
        this.mode = mode;
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
    }
    public GameSettings(ViewMode mode) {
        this(mode, 10, 10, 9);
    }
}
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

    }
}
