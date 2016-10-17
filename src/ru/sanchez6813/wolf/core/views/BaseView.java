package ru.sanchez6813.wolf.core.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by Alex on 17.10.2016.
 */
public class BaseView extends JPanel {
    private EventsListener eventsListener;

    public void setEventsListener(EventsListener eventsListener) {
        this.eventsListener = eventsListener;
    }

    // Конструктор
    public BaseView() {
        setBackground(Color.BLACK);
        // Подписываемся на события базового класса JPanel
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                // При изменении размера JPanel сообщаем об этом наружу с помощью EventsListener'а
                if (eventsListener != null) {
                    // Новая ширина и высота панели лежат в переданном сюда объекте события
                    int width = e.getComponent().getWidth();
                    int height = e.getComponent().getHeight();
                    eventsListener.onResize(width, height);
                }
            }

            // Остальные события нам не интересны, потому не реализовываем методы
            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }

    // Вызывается системой при перерисовке JPanel, будем использовать для отрисовки своих объектов
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Сообщаем наружу что пора рисовать объекты
        if (eventsListener != null) {
            eventsListener.onDraw(g);
        }
        g.dispose();
    }

    // Интерфейс для передачи сообщений наружу
    public interface EventsListener {
        void onDraw(Graphics g);

        void onResize(int width, int height);
    }
}