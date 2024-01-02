package year_2021.Day_06;

public class Fish {
    private int timer;

    public Fish(int timer) {
        this.timer = timer;
    }

    public Fish() {
        this.timer = 8;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void newDay() {
        if(this.timer == 0) {
            this.timer = 7;
        }
        this.timer--;
    }
}
