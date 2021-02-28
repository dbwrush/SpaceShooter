package me.davidrush.spaceshooter.graphics;

public class Toast {
    private int time;
    private String message;

    public Toast(int time, String message) {
        this.time = time;
        this.message = message;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
