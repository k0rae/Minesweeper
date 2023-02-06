package ru.nsu.ccfit.biryukovSergey.Minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Field {
    private final ArrayList<ArrayList<Cell>> field;
    private final int width;
    private final int height;

    public Field(GameSettings settings) {
        this.field = new ArrayList<>();
        this.width = settings.width;
        this.height = settings.height;
        for (int x = 0; x < width; x++) {
            field.add(new ArrayList<>());
            for(int y = 0; y < height; y++) {
                Cell cell = new Cell();
                field.get(x).add(cell);
            }
        }
    }
    public Cell GetCell(int x, int y) {
        try {
            return field.get(x).get(y);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    public List<Cell> GetSurroundingCells(Cell cell) {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(cell == GetCell(x, y)) {
                    return GetSurroundingCells(x, y);
                }
            }
        }
        return new ArrayList<>();
    }
    public List<Cell> GetSurroundingCells(int x, int y) {
        List<Cell> list = new ArrayList<Cell>();
        list.add(GetCell(x-1, y-1));
        list.add(GetCell(x, y-1));
        list.add(GetCell(x+1, y-1));
        list.add(GetCell(x-1, y));
        list.add(GetCell(x+1, y));
        list.add(GetCell(x-1, y+1));
        list.add(GetCell(x, y+1));
        list.add(GetCell(x+1, y+1));
        list.removeIf(Objects::isNull);
        return list;
    }

}
