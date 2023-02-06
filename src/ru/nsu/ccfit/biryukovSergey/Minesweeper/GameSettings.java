package ru.nsu.ccfit.biryukovSergey.Minesweeper;

public class GameSettings {
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
