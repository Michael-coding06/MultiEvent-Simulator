public class ArriveEvent extends Event {
    public ArriveEvent(Customer customer, double time) {
        super(time, customer);
    }

    @Override
    public Pair<Maybe<Event>, Shop> next(Shop shop) {
        return shop.findServer(this.customer).map(server -> {
            
            double serveTime = shop.getServiceTime().get();
            Shop updatedShop = shop.update(server.serve(this.customer, serveTime));

            double startServeTime = Math.max(this.customer.getArrivalTime(), 
                server.getAvailableTime());

            return new Pair<Maybe<Event>, Shop>(
                Maybe.of(new ServeEvent(this.customer, server, startServeTime, 
                    serveTime)), 
                updatedShop
            );
        })
        .orElse(
            shop.findCanWaitServer().map(server -> {
                Server updatedServer = server.handleWait(1); 
                Shop updatedShop = shop.update(updatedServer);

                return new Pair<Maybe<Event>, Shop>(
                    Maybe.of(new WaitEvent(this.customer, this.time, 
                        updatedServer, true)),
                    updatedShop
                );
            })
            .orElse(new Pair<Maybe<Event>, Shop>(
                Maybe.of(new LeaveEvent(this.customer, this.time)), 
                shop
            ))
        );
    }

    @Override
    public Statistics updateStatistics(Statistics stats) {
        return stats;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.time) + " customer " + 
            this.customer.getId() + " arrives";
    }
}