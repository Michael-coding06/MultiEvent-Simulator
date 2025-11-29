public class EndEvent extends Event {
    public EndEvent(Customer customer, double time) {
        super(time, customer);
    }

    @Override
    public Pair<Maybe<Event>, Shop> next(Shop shop) {
        return new Pair<>(Maybe.empty(), shop);
    }

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats;
    }

    @Override
    public String toString() {
        return "";
    }
}