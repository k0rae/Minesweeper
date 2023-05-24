package ru.nsu.ccfit.biryukovSergey.Minesweeper.View;

import ru.nsu.ccfit.biryukovSergey.Minesweeper.Cell;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.Exceptions.InvalidCommand;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.Field;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.GameController;
import ru.nsu.ccfit.biryukovSergey.Minesweeper.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GUIView implements View {
    private final Field field;
    private final GameSettings settings;
    private JFrame frame;
    private JPanel buttonPanel;
    private ArrayList<ArrayList<JButton>> buttons;
    private JPanel textPanel;
    private JLabel textLabel;
    private JLabel timerLabel;
    private final GameController controller;
    private void SwingInit() {
        frame = new JFrame();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(settings.height, settings.width)); // Изменен порядок width и height
        buttonPanel.setVisible(true);

        frame.add(buttonPanel);

        buttons = new ArrayList<ArrayList<JButton>>();
        for(int y = 0; y < settings.height; y++) {
            buttons.add(new ArrayList<JButton>());
            for(int x = 0; x < settings.width; x++) {
                JButton button = new JButton();
                buttons.get(y).add(button);
                button.setVisible(true);
                button.setText("");
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        for(int y = 0; y < settings.height; y++) {
                            for(int x = 0; x < settings.width; x++) {
                                if(e.getSource() == buttons.get(y).get(x)) {
                                    if(SwingUtilities.isLeftMouseButton(e)) {
                                        try {
                                            controller.PerformCommand(String.format("o %d-%d", x, y));
                                        } catch (InvalidCommand ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    } else if (SwingUtilities.isRightMouseButton(e)) {
                                        System.out.println("flag");
                                        try {
                                            controller.PerformCommand(String.format("f %d-%d", x, y));
                                        } catch (InvalidCommand ex) {
                                            throw new RuntimeException(ex);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
                buttonPanel.add(button);
            }
        }

        textPanel = new JPanel();
        textPanel.setVisible(true);
        frame.add(textPanel, BorderLayout.NORTH);
        textLabel = new JLabel();
        timerLabel = new JLabel();
        textPanel.add(timerLabel);
        textPanel.add(textLabel);
        textLabel.setText("Hello!");

        frame.revalidate();
    }
    public GUIView(Field field, GameSettings settings, GameController controller) {
        this.field = field;
        this.settings = settings;
        this.controller = controller;
        SwingInit();

    }
    @Override
    public void Draw() {
        for (int y = 0; y < settings.height; y++) {
            for (int x = 0; x < settings.width; x++) {
                JButton button = buttons.get(y).get(x);  // Исправлен порядок индексов
                Cell cell = field.GetCell(x, y);
                button.setEnabled(!cell.IsOpened());
                if (cell.IsFlagged()) {
                    button.setText("!");
                } else {
                    if (cell.IsOpened()) {
                        if (cell.GetNumber() == 0) {
                            button.setText("");
                        } else {
                            button.setText(String.valueOf(cell.GetNumber()));
                        }
                    } else {
                        button.setText("");
                    }
                }
            }
        }
        frame.revalidate();
    }
    public void updateTimerLabel(int currentTime) {
        timerLabel.setText("Time: " + currentTime);
    }
}
