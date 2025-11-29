public class Customer implements Comparable<Customer> {
    private final int identifier;
    private final double arrivalTime;

    public Customer(int identifier, double arrivalTime) {
        this.identifier = identifier;
        this.arrivalTime = arrivalTime;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public int getId() {
        return identifier;
    }
    
    public boolean canBeServed(double time) {
        return this.arrivalTime >= time;
    }

    public double serveTill(double time) {
        return this.arrivalTime + time;
    }

    @Override
    public int compareTo(Customer otherCustomer) {
        return Double.compare(this.arrivalTime, otherCustomer.arrivalTime);
    }
}