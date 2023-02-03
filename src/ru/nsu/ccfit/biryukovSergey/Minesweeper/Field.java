package ru.nsu.ccfit.biryukovSergey.Minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final ArrayList<ArrayList<Cell>> field;

    public Field(GameSettings settings) {
        this.field = new ArrayList<ArrayList<Cell>>();
        for (int x = 0; x < settings.width; x++) {
            field.add(new ArrayList<Cell>());
            for(int y = 0; y < settings.height; y++) {
                Cell cell = new Cell();
            }
        }
    }
    public Cell GetCell(int x, int y) throws IndexOutOfBoundsException {
        return field.get(x).get(y);
    }

}
