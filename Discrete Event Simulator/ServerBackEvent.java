package cs2030.simulator;

import java.util.List;

public class ServerBackEvent extends Event {
    
    public ServerBackEvent(Customer customer, 
            double startTime, 
            List<Server> servers, 
            Server eventServer) {
        
        super(customer, 
                startTime, 
                EventType.END_REST_EVENT, 
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

        Server newServer = new Server(eventServer.getID(), true, 
                eventServer.isAnyoneInQueue(), eventServer.getNextAvailableTime(), 
                eventServer.getMaxQueue(), eventServer.getQueue());
        this.getServers().set(this.getServers().indexOf(eventServer), newServer);
        
        return new ServerBackEvent(newServer.peek(), newServer.getNextAvailableTime(), 
                this.getServers(), newServer);
    }
}
