package ru.nsu.ccfit.biryukovSergey.Minesweeper;

import ru.nsu.ccfit.biryukovSergey.Minesweeper.Exceptions.InvalidCommand;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.View.ConsoleView;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.View.GUIView;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.View.View;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class GameController {
    private final GameSettings settings;
    private final Timer timer;
    private final Field field;
    private int flaggedMines;
    private final View view;
    private int availableFlags;
    private boolean firstCommand;
    public GameController(GameSettings settings) {
        this.settings = settings;
        field = new Field(settings);
        timer = new Timer(settings);
        if (settings.mode == ViewMode.GUI_VIEW) {
            this.view = new GUIView(field, settings, this);
        } else {
            this.view = new ConsoleView(field, settings);
        }
    }
    private void FillMines() {
        int placedMines = 0;
        while (placedMines != settings.mineCount) {
            int x = ThreadLocalRandom.current().nextInt(0, settings.width);
            int y = ThreadLocalRandom.current().nextInt(0, settings.height);
            if(field.GetCell(x, y).HasMine() || field.GetCell(x, y).IsOpened()) continue;
            field.GetCell(x, y).PlaceMine();
            placedMines++;
        }
    }
    private void GameLost() {
        System.out.println("You lost!");
        timer.end(true);
        System.out.printf("You've been playing for: %d\n", timer.getCounter());
        System.exit(0);
    }
    private void GameWon() {
        System.out.println("You won!");
        if (timer.end(false)) {
            System.out.printf("New best time: %d\n", timer.getCounter());
        } else {
            System.out.printf("Your time: %d\n", timer.getCounter());
        }
        System.exit(0);
    }
    private void CheckWin() {
        if (flaggedMines == settings.mineCount) GameWon();
    }
    private void OpenCell(Cell cell) {
        if(cell.HasMine()) GameLost();
        if(cell.IsOpened() || cell.IsFlagged()) return;
        cell.Open();
        if(cell.GetNumber() == 0) {
            field.GetSurroundingCells(cell).forEach(this::OpenCell);
        }
    }
    private void OpenCell(int x, int y) {
        Cell cell = field.GetCell(x, y);
        OpenCell(cell);
    }
    private void FlagCell(int x, int y) {
        Cell cell = field.GetCell(x, y);
        if(!cell.IsOpened()) {
            if(cell.HasMine()) {
                if(cell.IsFlagged()) {
                    flaggedMines--;
                } else {
                    flaggedMines++;
                };
            }
            if(cell.IsFlagged()) {
                availableFlags++;
                cell.ToggleFlag();
            } else if (availableFlags > 0) {
                availableFlags--;
                cell.ToggleFlag();
            };
        }
        CheckWin();
    }
    private void FillNumber() {
        for(int x = 0; x < settings.width; x++) {
            for(int y = 0; y < settings.height; y++) {
                Cell cell = field.GetCell(x, y);
                if(!cell.HasMine()) continue;
                List<Cell> around = field.GetSurroundingCells(x, y);
                around.forEach(c -> c.SetNumber(c.GetNumber()+1));
            }
        }
    }
    public void HandleCommand(String command) throws InvalidCommand {
        try {
            char action = command.charAt(0);
            int x = Integer.parseInt(command.split(" ")[1].split("-")[0]);
            int y = Integer.parseInt(command.split(" ")[1].split("-")[1]);
            if(action != 'o' && action != 'f') throw new InvalidCommand(String.format("Unrecognized command %s. Available commands: o(open), f(flag).", action));
            if(x > settings.width || x < 0 || y > settings.height || y < 0) throw new InvalidCommand("Out of bounds");
            if(action == 'o') {
                OpenCell(x, y);
            } else if (action == 'f') {
                FlagCell(x, y);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidCommand("Available commands: o(open), f(flag).");
        }

    }
    public void PerformCommand(String command) throws InvalidCommand {
        if(command.charAt(0) == 'o' && firstCommand) {
            FillMines();
            FillNumber();
            firstCommand = false;
        }
        HandleCommand(command);
        view.Draw();
    }
    private void GameLoop() {
        firstCommand = true;
        view.Draw();
        if(settings.mode == ViewMode.CONSOLE_VIEW) {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String cmd = scanner.nextLine();
                try {
                    if(cmd.charAt(0) == 'o' && firstCommand) {
                        FillMines();
                        FillNumber();
                        firstCommand = false;
                    }
                    HandleCommand(cmd);
                    view.Draw();
                } catch (InvalidCommand e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }
    public void Start() {
        flaggedMines = 0;
        availableFlags = this.settings.mineCount;
        Thread t = new Thread(timer);
        if(this.settings.mode == ViewMode.GUI_VIEW) {
            timer.setGUIView((GUIView) this.view);
        }
        t.start();
        timer.start();
        GameLoop();
    }
}
