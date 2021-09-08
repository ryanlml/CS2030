package cs2030.simulator;

import java.util.List;

public class ServeEvent extends Event {

    public ServeEvent(Customer customer, 
            double startTime, 
            List<Server> servers, 
            Server eventServer) {
        
        super(customer, 
                startTime, 
                EventType.SERVE_EVENT, 
                servers, 
                eventServer);
    }

    @Override
    public Event execute() {
        
        double serviceTime = this.getCustomer().getServiceTime();
        double nextAvailableTime = this.getStartTime() + serviceTime;
        
        Server eventServer = null;
        for (int i = 0; i < this.getServers().size(); i++) {
            if (this.getServers().get(i).getID() == this.getEventServer().getID()) {
                eventServer = this.getServers().get(i);
            }
        }

        if (eventServer.isAnyoneInQueue()) {
            eventServer.poll();
        }

        Server newServer = new Server(eventServer.getID(), false, 
                eventServer.isAnyoneInQueue(), nextAvailableTime, 
                eventServer.getMaxQueue(), eventServer.getQueue());
        this.getServers().set(this.getServers().indexOf(eventServer), newServer);
        
        return new DoneEvent(this.getCustomer(), nextAvailableTime, this.getServers(), newServer);
    }

    @Override
    public String toString() {
        return String.format("%s serves by server %d", 
                super.toString(), this.getEventServer().getID());
    }
}
