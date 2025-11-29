public class WaitEvent extends Event {
    private final Server server;
    private final boolean justArrived;

    public WaitEvent(Customer customer, double time, Server server, boolean justArrived) {
        super(time, customer);
        this.server = server;
        this.justArrived = justArrived;
    }

    @Override
    public Pair<Maybe<Event>, Shop> next(Shop shop) {
        Server currentServer = shop.getServer(this.server);

        if (currentServer.getAvailableTime() > this.time) {
            return new Pair<>(
                Maybe.of(new WaitEvent(this.customer, 
                    currentServer.getAvailableTime(), currentServer, false)),
                shop
            );
        }

        Double serviceTime = shop.getServiceTime().get();
        Server updatedServer = currentServer.serveWaiting(serviceTime);
        Shop updatedShop = shop.update(updatedServer); 

        return new Pair<>(
            Maybe.of(new ServeEvent(this.customer, currentServer, 
                currentServer.getAvailableTime(), serviceTime, true)),
            updatedShop
        );
    }

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats;
    }

    @Override
    public String toString() {
        if (this.justArrived) {
            return String.format("%.3f", this.time) + " customer "
                + this.customer.getId() + " waits at " + this.server;
        } else {
            return "";
        }
    }
}