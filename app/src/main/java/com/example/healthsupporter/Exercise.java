package com.example.healthsupporter;

public class Exercise {
    private String name;
    private int sets;
    private boolean completed;

    public Exercise(String name, int sets) {
        this.name = name;
        if (sets >= 0) {
            this.sets = sets;
        } else {
            this.sets = 0; // 음수인 경우 기본값으로 설정
        }
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
