class Statistics {
    private final double waitingTime;
    private final int numOfServed;
    private final int numOfLeft;

    public Statistics() {
        this(0, 0, 0);
    }

    public Statistics(double waitingTime, int numOfServed, int numOfLeft) {
        this.waitingTime = waitingTime;
        this.numOfServed = numOfServed;
        this.numOfLeft = numOfLeft;
    }

    public Statistics addTime(double time) {
        return new Statistics(this.waitingTime + time, this.numOfServed, this.numOfLeft);
    }

    public Statistics addLeft() {
        return new Statistics(this.waitingTime, this.numOfServed, this.numOfLeft + 1);
    }

    public Statistics addServed() {
        return new Statistics(this.waitingTime, this.numOfServed + 1, this.numOfLeft);
    }

    @Override
    public String toString() {
        double average;
        if (this.numOfServed == 0) {
            average = 0;
        } else {
            average = this.waitingTime / this.numOfServed;
        }
        return String.format("[%.3f %d %d]", average, 
            this.numOfServed, this.numOfLeft);
    }
}