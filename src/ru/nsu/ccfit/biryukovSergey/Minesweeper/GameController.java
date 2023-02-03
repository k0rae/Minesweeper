package ru.nsu.ccfit.biryukovSergey.Minesweeper;

import ru.nsu.ccfit.biryukovSergey.Minesweeper.Exceptions.InvalidCommand;

import java.util.concurrent.ThreadLocalRandom;

public class GameController {
    private final GameSettings settings;
    private final Field field;
    private int flaggedMines;
    public GameController(GameSettings settings) {
        this.settings = settings;
        field = new Field(settings);
    }
    private void FillMines() {
        int placedMines = 0;
        while (placedMines != settings.mineCount) {
            int x = ThreadLocalRandom.current().nextInt(0, settings.width+1);
            int y = ThreadLocalRandom.current().nextInt(0, settings.height+1);
            if(field.GetCell(x, y).HasMine()) continue;
            field.GetCell(x, y).PlaceMine();
            placedMines++;
        }
    }
    private void GameLost() {

    }
    private void GameWon() {

    }
    private void CheckWin() {
        if (flaggedMines == settings.mineCount) GameWon();
    }
    private void OpenCell(int x, int y) {
        Cell cell = field.GetCell(x, y);
        if(cell.HasMine()) GameLost();

    }
    private void FlagCell(int x, int y) {
        Cell cell = field.GetCell(x, y);
        if(!cell.IsOpened()) {
            if(cell.HasMine()) {
                if(cell.IsFlagged()) {
                    flaggedMines--;
                } else flaggedMines++;
            }
            cell.ToggleFlag();
        }
        CheckWin();
    }
    public void HandleCommand(String command) throws InvalidCommand {
        char action = command.charAt(0);
        int x = Integer.parseInt(command.split(" ")[1].split("-")[0]);
        int y = Integer.parseInt(command.split(" ")[1].split("-")[1]);
        if(action != 'o' && action != 'f') throw new InvalidCommand("Available commands: o(open), f(flag).");
        if(x > settings.width || x < 0 || y > settings.height || y < 0) throw new InvalidCommand("Out of bounds");
        if(action == 'o') {
            OpenCell(x, y);
        } else if (action == 'f') {
            FlagCell(x, y);
        }
    }
    public void Init() {
        FillMines();
        flaggedMines = 0;
    }
}
