package cs2030.simulator;

import java.util.List;

public class DoneEvent extends Event {
    
    public DoneEvent(Customer customer, double startTime,
            List<Server> servers, Server eventServer) {
        super(customer, startTime, EventType.DONE_EVENT, servers, eventServer);
    }

    @Override
    public Event execute() {
        Server eventServer = null;
        for (int i = 0; i < this.getServers().size(); i++) {
            if (this.getServers().get(i).getID() == this.getEventServer().getID()) {
                eventServer = this.getServers().get(i);
            }
        }

        Customer nextCustomer = eventServer.peek();
        if (eventServer.isAnyoneInQueue()) {
            Server newServer = new Server(eventServer.getID(), false, 
                    eventServer.hasWaitingCustomer(), eventServer.getNextAvailableTime(), 
                    eventServer.getMaxQueue(), eventServer.getQueue());
            this.getServers().set(this.getServers().indexOf(eventServer), newServer);
            return new DoneEvent(nextCustomer, 
                    newServer.getNextAvailableTime(), this.getServers(), newServer);
        } else {
            Server newServer = new Server(eventServer.getID(), true, 
                    false, eventServer.getNextAvailableTime(), 
                    eventServer.getMaxQueue(), eventServer.getQueue());
            this.getServers().set(this.getServers().indexOf(eventServer), newServer); 
            return new DoneEvent(this.getCustomer(), 
                    newServer.getNextAvailableTime(), this.getServers(), newServer);
        }
    }

    @Override
    public String toString() {
        return String.format("%s done serving by server %d", 
                super.toString(), this.getEventServer().getID());
    }
}
