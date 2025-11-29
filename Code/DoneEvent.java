public class DoneEvent extends Event {
    public DoneEvent(Customer customer, double time) {
        super(time, customer);
    }

    @Override
    public Pair<Maybe<Event>, Shop> next(Shop shop) {
        return new Pair<>(Maybe.of(new EndEvent(this.customer, this.time)), shop);
    }

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats.addServed();
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " customer " + 
            this.customer.getId() + " done";
    }
}