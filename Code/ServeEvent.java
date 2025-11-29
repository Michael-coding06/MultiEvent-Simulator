
public class ServeEvent extends Event {
    private final Server server;
    private final Double serveTime;
    private final boolean fromWaiting;

    public ServeEvent(Customer customer, Server server, double time, Double serveTime) {
        super(time, customer);
        this.server = server;
        this.serveTime = serveTime;
        this.fromWaiting = false;
    }

    public ServeEvent(Customer customer, Server server, double time, 
            Double serveTime, boolean fromWaiting) {
        super(time, customer);
        this.server = server;
        this.serveTime = serveTime;
        this.fromWaiting = fromWaiting;
    }

    public Server getServer() {
        return this.server;
    }

    @Override
    public Pair<Maybe<Event>, Shop> next(Shop shop) {
        Shop updatedShop = shop;
        if (this.fromWaiting) {
            Server currentServer = shop.getServer(this.server);
            updatedShop = shop.update(currentServer.handleWait(-1));
        }

        return new Pair<>(
            Maybe.of(new DoneEvent(this.customer, this.time + this.serveTime)),
            updatedShop
        );
    }

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats.addTime(this.time - this.customer.getArrivalTime());
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " customer "
            + this.customer.getId() + " serves by " + this.server;
    }
}