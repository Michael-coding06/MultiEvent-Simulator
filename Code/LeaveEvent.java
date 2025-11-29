public class LeaveEvent extends Event {
    public LeaveEvent(Customer customer, double time) {
        super(time, customer);
    }

    @Override
    public Pair<Maybe<Event>, Shop> next(Shop shop) {
        return new Pair<>(Maybe.empty(), shop);
    }

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats.addLeft();
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " customer " + 
            this.customer.getId() + " leaves";
    }
}