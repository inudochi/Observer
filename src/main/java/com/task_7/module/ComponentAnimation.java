package com.task_7.module;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ComponentAnimation implements Observer {
    private GraphicsContext graphicsContext;
    private int period = 20; // По умолчанию 20 секунд
    private int lastAnimatedTime = 0;
    private boolean isActive = false;
    private Timeline timeline;
    private double progress = 0.0;

    public ComponentAnimation(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    @Override
    public void update(Subject st) {
        if (isActive) {
            TimeServer timeServer = (TimeServer) st;
            if (timeServer.getState() - lastAnimatedTime >= period) {
                animate();
                lastAnimatedTime = timeServer.getState();
            }
        }
    }

    private void animate() {
        // Очистка холста
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());

        // Установка цвета заливки
        graphicsContext.setFill(Color.CORAL);

        // Рисование кругового индикатора
        double centerX = graphicsContext.getCanvas().getWidth() / 2;
        double centerY = graphicsContext.getCanvas().getHeight() / 2;
        double radius = 50;

        // Рисование фона кругового индикатора
        graphicsContext.setStroke(Color.LIGHTGRAY);
        graphicsContext.setLineWidth(10);
        graphicsContext.strokeOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        // Рисование прогресса кругового индикатора
        graphicsContext.setStroke(Color.LIGHTGREEN);
        graphicsContext.strokeArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 90, -progress * 360, javafx.scene.shape.ArcType.OPEN);

        // Создание анимации прогресса
        timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            progress += 0.1; // Увеличиваем прогресс на 10% каждые 20 секунд
            if (progress > 1.0) {
                progress = 0.0; // Сбрасываем прогресс, если он достиг 100%
            }
            animate();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void start(int period) {
        this.period = period;
        isActive = true;
        animate(); // Запуск анимации сразу при старте
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
        isActive = false;
    }
}