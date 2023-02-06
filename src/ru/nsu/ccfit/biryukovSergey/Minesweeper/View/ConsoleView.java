package ru.nsu.ccfit.biryukovSergey.Minesweeper.View;

import ru.nsu.ccfit.biryukovSergey.Minesweeper.Cell;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.Field;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.GameSettings;

public class ConsoleView implements View {
    private final Field field;
    private final GameSettings settings;
    public ConsoleView(Field field, GameSettings settings) {
        this.field = field;
        this.settings = settings;
    }
    private void DrawCell(int x, int y) {
        Cell cell = field.GetCell(x, y);
        String cellStr = cell.HasMine() ? "9" : Integer.toString(cell.GetNumber());
        if(cell.IsOpened()) {
            if(cell.GetNumber() == 0 && !cell.HasMine()) cellStr = " ";
        } else{
            cellStr = "#";
        }
        if(cell.IsFlagged()) cellStr = "!";
        System.out.printf("%3s", cellStr + " ");
    }
    @Override
    public void Draw() {
        System.out.printf("%4s", "☺| ");
        for(int i = 0; i < settings.width; i++) {
            System.out.printf("%3s", i + " ");
        }
        System.out.println();
        for(int i = 0; i < settings.width+1; i++) {
            System.out.printf("%s", "---");
        }
        System.out.println();
        for(int y = 0; y < settings.height; y++) {
            System.out.printf("%4s", y + "| ");
            for(int x = 0; x < settings.width; x++) {
                DrawCell(x, y);
            }
            System.out.println();
        }
    }

}
