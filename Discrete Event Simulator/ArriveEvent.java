package cs2030.simulator;

import java.util.List;

public class ArriveEvent extends Event {

    public ArriveEvent(Customer customer, List<Server> servers) {
        super(customer, customer.getArrivalTime(), EventType.ARRIVE_EVENT, servers);
    }

    @Override
    public Event execute() {
        for (int i = 0; i < this.getServers().size(); i++) {
            Server curServer = this.getServers().get(i);
            if (curServer.isAvailable()) {
                return new ServeEvent(this.getCustomer(), 
                        this.getCustomer().getArrivalTime(), this.getServers(), curServer);
            }
        }

        if (this.getCustomer().isGreedy()) {
            int maxAvailableSlots = this.getServers().get(0).getMaxQueue();
            int index = -1;
            for (int i = 0; i < this.getServers().size(); i++) {
                if (this.getServers().get(i).getQueue().size() < maxAvailableSlots) {
                    index = i;
                    maxAvailableSlots = this.getServers().get(i).getQueue().size();
                }
            }
            if (index == -1) {
                return new LeaveEvent(this.getCustomer(), 
                        this.getCustomer().getArrivalTime(), this.getServers());
            }
            return new WaitEvent(this.getCustomer(), 
                    this.getCustomer().getArrivalTime(), 
                    this.getServers(), this.getServers().get(index));
        }

        for (int i = 0; i < this.getServers().size(); i++) {
            Server curServer = this.getServers().get(i);
            if (curServer.isQueueable()) {
                return new WaitEvent(this.getCustomer(), 
                        this.getCustomer().getArrivalTime(), this.getServers(), curServer);
            }
        }
        
        for (int i = 0; i < this.getServers().size(); i++) {
            Server curServer = this.getServers().get(i);
            if (!curServer.hasWaitingCustomer()) {
                return new WaitEvent(this.getCustomer(), 
                        this.getCustomer().getArrivalTime(), this.getServers(), curServer);
            }
        }

        return new LeaveEvent(this.getCustomer(), 
                this.getCustomer().getArrivalTime(), this.getServers());
    }

    @Override
    public String toString() {
        return String.format("%s arrives", super.toString());
    }
}
