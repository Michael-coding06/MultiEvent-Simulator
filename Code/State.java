public class State {
    private final PQ<Event> pq;
    private final Shop shop;
    private final String output;
    private final boolean isEmpty;
    private final Statistics statistics;

    public State(PQ<Event> pq, Shop shop) {
        this.pq = pq;
        this.shop = shop;
        this.output = "";
        this.isEmpty = false;
        this.statistics = new Statistics();
    }

    private State(PQ<Event> pq, String output, Shop shop, 
                boolean isEmpty, Statistics statistics) {
        this.output = output;
        this.pq = pq;
        this.shop = shop;
        this.isEmpty = isEmpty;
        this.statistics = statistics;
    }

    public State next() {
        Pair<Maybe<Event>, PQ<Event>> result = this.pq.poll();
        Maybe<Event> currentEvent = result.t();  
        PQ<Event> remainingEvents = result.u();

        return currentEvent.map(e -> {
            String finalOutput = this.output;
            if (!e.toString().isEmpty()) {
                finalOutput += e.toString() + "\n";
            }

            Pair<Maybe<Event>, Shop> nextPair = e.next(this.shop);

            PQ<Event> updatedEvents = nextPair.t()
                .map(nextEvent -> remainingEvents.add(nextEvent))
                .orElse(remainingEvents);

            Shop updatedShop = nextPair.u();

            Statistics updatedStatistics = e.updateStatistics(this.statistics);

            return new State(updatedEvents, finalOutput, updatedShop, false, 
                updatedStatistics);
        }).orElse(new State(this.pq, this.output, this.shop, true, this.statistics));
    }

    public boolean isEmpty() {
        return this.isEmpty;
    }

    public String getStatistics() {
        return this.statistics.toString();
    }

    @Override
    public String toString() {
        return this.output;
    }
}