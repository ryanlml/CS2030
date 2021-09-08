package cs2030.simulator;

import java.util.List;

public class ServerRestEvent extends Event {
    
    private final double breakTime;
    
    public ServerRestEvent(Customer customer, 
            double startTime, 
            List<Server> servers, 
            Server eventServer, 
            double breakTime) {
        
        super(customer, 
                startTime, 
                EventType.START_REST_EVENT, 
                servers, 
                eventServer);
        
        this.breakTime = breakTime;
    }

    @Override
    public Event execute() {
        Server eventServer = null;
        for (int i = 0; i < this.getServers().size(); i++) {
            if (this.getServers().get(i).getID() == this.getEventServer().getID()) {
                eventServer = this.getServers().get(i);
            }
        }

        Server newServer = new Server(eventServer.getID(), false, 
                eventServer.isAnyoneInQueue(), this.getStartTime() + this.breakTime, 
                eventServer.getMaxQueue(), eventServer.getQueue());
        this.getServers().set(this.getServers().indexOf(eventServer), newServer);
        
        return new ServerBackEvent(this.getCustomer(), newServer.getNextAvailableTime(), 
                this.getServers(), newServer);
    }
}
