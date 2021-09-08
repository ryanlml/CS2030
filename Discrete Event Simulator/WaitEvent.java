package cs2030.simulator;

import java.util.List;

public class WaitEvent extends Event {
    
    public WaitEvent(Customer customer, 
            double startTime, 
            List<Server> servers, 
            Server eventServer) {
        
        super(customer, 
                startTime, 
                EventType.WAIT_EVENT, 
                servers, 
                eventServer);
    }

    @Override
    public Event execute() {
        Server eventServer = null;
        for (int i = 0; i < this.getServers().size(); i++) {
            if (this.getServers().get(i).getID() == this.getEventServer().getID()) {
                eventServer = this.getServers().get(i);
            }
        }

        eventServer = eventServer.addToQueue(this.getCustomer());
        Server newServer = new Server(eventServer.getID(), 
                eventServer.isAvailable(), true, 
                eventServer.getNextAvailableTime(), 
                eventServer.getMaxQueue(), eventServer.getQueue());
        this.getServers().set(newServer.getID() - 1, newServer);
        
        return new ServeEvent(this.getCustomer(), 
                newServer.getNextAvailableTime(), 
                this.getServers(), 
                newServer);
    }

    @Override
    public String toString() {
        return String.format("%s waits at server %d", 
                super.toString(), this.getEventServer().getID());
    }

}
