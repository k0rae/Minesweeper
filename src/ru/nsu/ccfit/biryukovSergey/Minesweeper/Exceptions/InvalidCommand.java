package ru.nsu.ccfit.biryukovSergey.Minesweeper.Exceptions;

public class InvalidCommand extends Exception {
    public InvalidCommand(String errorMsg) {
        super(errorMsg);
    }
}
