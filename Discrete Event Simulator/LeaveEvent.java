package cs2030.simulator;

import java.util.List;

public class LeaveEvent extends Event {

    public LeaveEvent(Customer customer, double startTime, List<Server> servers) {
        super(customer, startTime, EventType.LEAVE_EVENT, servers);
    }

    @Override
    public Event execute() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s leaves", super.toString());
    }
}
