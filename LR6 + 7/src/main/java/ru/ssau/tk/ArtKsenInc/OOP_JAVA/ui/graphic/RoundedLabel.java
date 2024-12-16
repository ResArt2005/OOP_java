package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic;

import javax.swing.*;
import java.awt.*;

public class RoundedLabel extends JLabel {
    private int borderRadius;
    private Color borderColor;

    // Конструктор с параметром цвета бордера
    public RoundedLabel(String text, int borderRadius, Color borderColor) {
        super(text, SwingConstants.CENTER); // Центрирование текста
        this.borderRadius = borderRadius;
        this.borderColor = borderColor;
        setOpaque(true); // Устанавливаем непрозрачность в true, чтобы фон был виден
        setBackground(ConstantColors.RICH_PURPLE); // Устанавливаем цвет заливки
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем закругленный прямоугольник
        g2d.setColor(getBackground());
        g2d.fillRoundRect(1, 1, getWidth() - 10, getHeight() - 10, borderRadius, borderRadius);

        // Рисуем бордер
        g2d.setColor(borderColor);
        g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, borderRadius, borderRadius);

        super.paintComponent(g);
    }
}
