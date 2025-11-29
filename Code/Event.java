public abstract class Event implements Comparable<Event> {
    protected final double time;
    protected final Customer customer;
    private static final double EPSILON = 1e-15;

    public Event(double eventTime, Customer customer) {
        this.time = eventTime;
        this.customer = customer;
    }
    
    public abstract Pair<Maybe<Event>, Shop> next(Shop shop);

    public abstract Statistics updateStatistics(Statistics stats);
    
    @Override
    public int compareTo(Event otherEvent) {
        if (Math.abs(this.time - otherEvent.time) < this.EPSILON) {
            return this.customer.compareTo(otherEvent.customer);
        }
        return Double.compare(this.time, otherEvent.time);
    }
}