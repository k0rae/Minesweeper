import ru.nsu.ccfit.biryukovSergey.Minesweeper.Game;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.GameSettings;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.ViewMode;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(new GameSettings(ViewMode.CONSOLE_VIEW));
        try {
            game.Start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}