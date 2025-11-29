
public class Server {
    private final int id;
    private final double availableTime;
    private static final double EPSILON = 1e-15;
    private final int queueLength;

    public Server(int id, int queueLength) {
        this.id = id;
        this.availableTime = 0.0;
        this.queueLength = queueLength;
    }

    private Server(int id, double availableTime, int queueLength) {
        this.id = id;
        this.availableTime = availableTime;
        this.queueLength = queueLength;
    }

    public double getAvailableTime() {
        return this.availableTime;
    }

    public Server serve(Customer customer, Double serviceTime) {
        if (canServe(customer)) {
            double serviceStartTime = Math.max(customer.getArrivalTime(), this.availableTime);
            return new Server(this.id, serviceStartTime + serviceTime, 
                this.queueLength);
        }
        return this;
    }

    public Server serveWaiting(Double serviceTime) {
        // double time = serviceTime.get();
        return new Server(this.id, this.availableTime + serviceTime, this.queueLength);
    }

    public Server handleWait(int num) {
        return new Server(this.id, this.availableTime, this.queueLength - num);
    }

    public boolean canServe(Customer customer) {
        return customer.getArrivalTime() > this.availableTime 
                || Math.abs(customer.getArrivalTime() - this.availableTime) < this.EPSILON;
    }

    public boolean canWait() {
        return this.queueLength > 0;
    }

    public boolean checkSame(Server other) {
        return this.id == other.id;
    }
    
    @Override
    public String toString() {
        return "server " + this.id;
    }
}