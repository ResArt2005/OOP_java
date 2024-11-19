package ru.ssau.tk.ArtKsenInc.OOP_JAVA.ui.graphic;

import javax.swing.*;
import java.awt.*;

public class ButtonsDesign {

    // Метод для создания стилизованной кнопки с эффектом наведения
    public static JButton createStyledButton(String text, Font font, Color BGColor, Color FGColor, Cursor cursorState) {
        JButton button = new JButton(text);
        button.setFont(font);  // Шрифт Open Sans Bold
        button.setBackground(BGColor);  // Фиолетовый цвет фона кнопки
        button.setForeground(FGColor);  // Голубой цвет текста
        button.setFocusPainted(true);
        //button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Пространство вокруг текста

        // Устанавливаем курсор pointer при наведении
        button.setCursor(cursorState);

        return button;
    }
}
