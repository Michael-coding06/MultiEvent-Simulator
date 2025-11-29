import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final InfList<Pair<Integer, Double>> arrivals;
    private final Supplier<Double> serviceTime;
    private final int queueLength;

    public Simulator(int numOfServers, int queueLength,
                     Supplier<Double> serviceTime, int numOfCustomers, 
                     InfList<Pair<Integer, Double>> arrivals) {
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
        this.serviceTime = serviceTime;
        this.queueLength = queueLength;
    }

    public Maybe<Pair<String, String>> run() {
        PQ<Event> pq = arrivals
            .map(x -> new Customer(x.t(), x.u()))
            .map(customer -> new ArriveEvent(customer, customer.getArrivalTime()))
            .reduce(new PQ<Event>(), (events, event) -> events.add(event));

        State output = InfList.iterate(
                new State(pq, new Shop(this.numOfServers, this.serviceTime, 
                    this.queueLength)),
                state -> state.next()
            )
            .takeWhile(state -> !state.isEmpty())
            .reduce(
                new State(new PQ<Event>(), new Shop(this.numOfServers, 
                    this.serviceTime, this.queueLength)),
                (prev, curr) -> curr
            );
        
        String stateString = output.toString();
        String statString = output.getStatistics();

        return Maybe.of(new Pair<>(stateString, statString));
    }
}