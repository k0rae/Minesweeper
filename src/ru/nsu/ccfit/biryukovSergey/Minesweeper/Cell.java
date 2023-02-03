package ru.nsu.ccfit.biryukovSergey.Minesweeper;

public class Cell {
    private boolean flagged;
    private boolean mine;
    private boolean opened;
    private int number;
    public boolean IsFlagged() { return flagged; }
    public void ToggleFlag() { this.flagged = !this.flagged; }
    public boolean IsOpened() { return this.opened; }
    public int GetNumber() { return this.number; }
    public void SetNumber(int number) { this.number = number; }
    public void PlaceMine() { this.mine = true; }
    public boolean HasMine() { return this.mine; }
    public boolean IsMineAndFlagged() { return this.mine && this.flagged; }
}
