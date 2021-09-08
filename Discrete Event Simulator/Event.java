package cs2030.simulator;

import java.util.List;

/**
 * An abstract Event class encapsulates the data related to an event.
 */
public abstract class Event implements Comparable<Event> {
    
    private final Customer customer;
    private final double startTime;
    private final EventType eventType;
    private final List<Server> servers;
    private final Server eventServer;

    /**
     * Constructor for an event.
     * @param customer The customer of the event.
     * @param startTime The starting time of the event.
     * @param eventType The type of event.
     * @param servers List of all servers available.
     * @param eventServer The server of the event.
     */
    public Event(Customer customer, 
            double startTime, 
            EventType eventType, 
            List<Server> servers, 
            Server eventServer) {

        this.customer = customer;
        this.startTime = startTime;
        this.eventType = eventType;
        this.servers = servers;
        this.eventServer = eventServer;
    }

    /**
     * Constructor for an event with no associated server.
     * @param customer The customer of the event.
     * @param startTime The starting time of the event.
     * @param eventType The type of event.
     * @param servers List of all servers available.
     */
    public Event(Customer customer, double startTime, EventType eventType, List<Server> servers) {
        this(customer, startTime, eventType, servers, null);
    }

    public abstract Event execute();

    public Server getEventServer() {
        return this.eventServer;
    }

    public double getStartTime() {
        return this.startTime;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public List<Server> getServers() {
        return this.servers;
    }

    public EventType type() {
        return this.eventType;   
    }
    
    /**
     * Compares the event with the specified event for priority.
     * @param other the event to be compared.
     * @return a negative integer, zero, or a positive integer as this event is more important, 
     *     equally important, or less important than the specified event.
     */
    @Override
    public int compareTo(Event other) {
        if (this.getStartTime() != other.getStartTime()) {
            return this.getStartTime() < other.getStartTime() ? -1 : 1;
        } else if (this.getCustomer().getID() != other.getCustomer().getID()) {
            return this.getCustomer().getID() - other.getCustomer().getID();
        } else {
            return other.type().compareTo(this.type());
        }
    }

    @Override
    public String toString() {
        String result = String.format("%.3f %d", this.getStartTime(), this.getCustomer().getID());
        return this.getCustomer().isGreedy() ? result + "(greedy)" : result;
    }
}
