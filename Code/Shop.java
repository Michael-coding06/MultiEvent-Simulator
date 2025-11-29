import java.util.function.Supplier;

public class Shop {
    private final int numOfServers;
    private final InfList<Server> servers;
    private final int queueLength;
    private final Supplier<Double> serviceTime;

    public Shop(int numOfServers, Supplier<Double> serviceTime, int queueLength) {
        this.numOfServers = numOfServers;
        this.servers = InfList.iterate(1, x -> x + 1)
                              .limit(numOfServers)
                              .map(i -> new Server(i, queueLength));
        this.queueLength = queueLength;
        this.serviceTime = serviceTime;
    }

    private Shop(int numOfServers, Supplier<Double> serviceTime, 
            InfList<Server> servers, int queueLength) {
        this.numOfServers = numOfServers;
        this.servers = servers;
        this.queueLength = queueLength;
        this.serviceTime = serviceTime;
    }

    public Supplier<Double> getServiceTime() {
        return this.serviceTime;
    }

    public Shop update(Server updateServer) {
        InfList<Server> updatedServers = this.servers.map(server ->
            server.checkSame(updateServer) ? updateServer : server);
        return new Shop(this.numOfServers, this.serviceTime, updatedServers, 
            this.queueLength);
    }

    public Maybe<Server> findServer(Customer customer) {
        return this.servers.filter(server -> server.canServe(customer)).findFirst();
    }

    public Maybe<Server> findCanWaitServer() {
        return this.servers.filter(server -> server.canWait()).findFirst();
    }

    public Server getServer(Server waitingServer) {
        return this.servers.filter(server -> server.checkSame(waitingServer))
            .findFirst()
            .orElse(waitingServer);
    }

    @Override
    public String toString() {
        String output = "Shop:";
        for (int i = 1; i <= this.numOfServers; i++) {
            output += "<server " + i + ">";
        }
        return output;
    }
}