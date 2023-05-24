import ru.nsu.ccfit.biryukovSergey.Minesweeper.Game;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.GameSettings;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.ViewMode;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int mineCount = 10;
        int width = 9;
        int height = 9;
        ViewMode mode = ViewMode.CONSOLE_VIEW;
        if (args.length == 4) {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            mineCount = Integer.parseInt(args[2]);
            mode = args[3].equals("gui") ? ViewMode.GUI_VIEW : ViewMode.CONSOLE_VIEW;
        }
        Game game = new Game(new GameSettings(mode, width, height, mineCount));
        try {
            game.Start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}