import cs2030.simulator.ArriveEvent;
import cs2030.simulator.Customer;
import cs2030.simulator.DoneEvent;
import cs2030.simulator.Event;
import cs2030.simulator.EventType;
import cs2030.simulator.LeaveEvent;
import cs2030.simulator.RNG;
import cs2030.simulator.ServeEvent;
import cs2030.simulator.Server;
import cs2030.simulator.ServerBackEvent;
import cs2030.simulator.ServerRestEvent;
import cs2030.simulator.WaitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main4 {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int customersServed = 0;
        int customersLeft = 0;
        double totalWaitingTime = 0;

        int numOfServers = sc.nextInt();
        int maxQueueLength = sc.nextInt();
        int numOfCustomers = sc.nextInt();
     
        List<Server> servers = new ArrayList<>();
        PriorityQueue<Event> pq = new PriorityQueue<>();
        PriorityQueue<Event> printable = new PriorityQueue<>();

     
        for (int i = 0; i < numOfServers; i++) {
            Server server = new Server(i + 1, true, false, 0, maxQueueLength);
            servers.add(server);
        }

        for (int i = 0; i < numOfCustomers; i++) {
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            Customer customer = new Customer(i + 1, arrivalTime, () -> serviceTime);
            Event event = new ArriveEvent(customer, servers);
            pq.offer(event);
        }
        
 
        while (pq.size() != 0) {

            Event event = pq.poll();
            printable.offer(event); 
            Event nextEvent = event.execute();
            servers = nextEvent.getServers();
            Server eventServer = null;

            switch (event.type()) {

                case ARRIVE_EVENT:
                    pq.offer(nextEvent); 
                    break;
                
                case SERVE_EVENT:
                    customersServed++;
                    totalWaitingTime += event.getStartTime() - event.getCustomer().getArrivalTime();
                    pq.offer(nextEvent);
                    break;

                case DONE_EVENT:
                    for (int i = 0; i < servers.size(); i++) {
                        if (servers.get(i).getID() == nextEvent.getEventServer().getID()) {
                            eventServer = servers.get(i);
                        }
                    }

                    double breakLength = sc.nextDouble();

                    if (breakLength != 0) {
                        pq.offer(new ServerRestEvent(nextEvent.getCustomer(), 
                                    nextEvent.getStartTime(), 
                                    servers, 
                                    nextEvent.getEventServer(), 
                                    breakLength));
                    } else if (eventServer.isAnyoneInQueue()) {
                        pq.offer(new ServeEvent(nextEvent.getCustomer(), 
                                    nextEvent.getStartTime(), 
                                    servers, 
                                    nextEvent.getEventServer()));
                    }
                    break;

                case START_REST_EVENT:
                    printable.remove(event);
                    pq.offer(nextEvent);
                    break;

                case END_REST_EVENT:
                    printable.remove(event);
    
                    for (int i = 0; i < servers.size(); i++) {
                        if (servers.get(i).getID() == nextEvent.getEventServer().getID()) {
                            eventServer = servers.get(i);
                        }
                    }

                    if (eventServer.isAnyoneInQueue()) {
                        pq.offer(new ServeEvent(nextEvent.getCustomer(), nextEvent.getStartTime(), 
                                    servers, nextEvent.getEventServer()));
                    }
                    break;
                case LEAVE_EVENT:
                    customersLeft++;
                    break;

                default:
                    break;
            }
        }

        for (Event e : printable) {
            System.out.println(e);
        }

        String statistics = String.format("[%.3f %d %d]", totalWaitingTime / customersServed, 
                customersServed, customersLeft);
        System.out.println(statistics);

    }
}
