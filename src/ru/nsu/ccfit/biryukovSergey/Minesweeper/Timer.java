package ru.nsu.ccfit.biryukovSergey.Minesweeper;

import ru.nsu.ccfit.biryukovSergey.Minesweeper.View.GUIView;

import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicBoolean;

public class Timer implements Runnable {
    private AtomicBoolean running;
    private int counter;
    private GameSettings gameSettings;
    private GUIView guiView;

    public Timer(GameSettings gameSettings) {
        this.running = new AtomicBoolean(false);
        this.gameSettings = gameSettings;
    }


    public void setGUIView(GUIView guiView) {
        this.guiView = guiView;
    }
    public void start() {
        running.set(true);
        counter = 0;
    }

    public boolean end(boolean lost) {
        running.set(false);
        if (lost) return false;
        return updateHighScore();
    }

    private boolean updateHighScore() {
        boolean isNewHighScore = false;

        try {
            String highScoresFilePath = "high_scores.txt";
            File file = new File(highScoresFilePath);

            if (!file.exists()) {
                // Если файл не существует, создаем его
                file.createNewFile();
            }

            Path path = Path.of(highScoresFilePath);
            String content = Files.readString(path);

            StringBuilder updatedFileContent = new StringBuilder();
            boolean isUpdated = false;
            int settingsIndex = -1;

            if (!content.isEmpty()) {
                String[] lines = content.split(System.lineSeparator());

                // Ищем индекс настроек в файле, если они уже присутствуют
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    String[] parts = line.split(",");
                    int scoreWidth = Integer.parseInt(parts[0]);
                    int scoreHeight = Integer.parseInt(parts[1]);
                    int scoreMines = Integer.parseInt(parts[2]);

                    if (scoreWidth == gameSettings.width && scoreHeight == gameSettings.height && scoreMines == gameSettings.mineCount) {
                        settingsIndex = i;
                        break;
                    }
                }
            }

            if (settingsIndex != -1) {
                // Настройки уже присутствуют в файле
                String[] parts = content.split(System.lineSeparator())[settingsIndex].split(",");
                int scoreCounter = Integer.parseInt(parts[3]);

                if (counter < scoreCounter) {
                    // Обновляем только если текущий счет лучше
                    String updatedLine = gameSettings.width + "," + gameSettings.height + "," + gameSettings.mineCount + "," + counter;
                    content = content.replace(content.split(System.lineSeparator())[settingsIndex], updatedLine);
                    isNewHighScore = true;
                    isUpdated = true;
                }
            } else {
                // Настройки отсутствуют в файле
                String newLine = gameSettings.width + "," + gameSettings.height + "," + gameSettings.mineCount + "," + counter;
                content += System.lineSeparator() + newLine;
                isNewHighScore = true;
                isUpdated = true;
            }

            // Записываем обновленный контент в файл
            if (isUpdated) {
                Files.writeString(path, content, StandardOpenOption.WRITE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return isNewHighScore;
    }
    public int getCounter() {
        return counter;
    }

    @Override
    public void run() {
        while (running.get()) {
            counter++;
            if (guiView != null) {
                SwingUtilities.invokeLater(() -> {
                    guiView.updateTimerLabel(counter);
                });
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}