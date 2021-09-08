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

public class Main5 {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numServed = 0;
        int numLeft = 0;
        double totalWaitingTime = 0;

        int numOfServers = sc.nextInt();
        int maxQueue = sc.nextInt();
        int numOfCustomers = sc.nextInt();
        int seed = sc.nextInt();
        double arrivalRate = sc.nextDouble();
        double serviceRate = sc.nextDouble();
        double restingRate = sc.nextDouble();
        double probabilityRest = sc.nextDouble();
        double probabilityGreedy = sc.nextDouble();
    
        RNG rng = new RNG(seed, arrivalRate, serviceRate, restingRate);
        PriorityQueue<Event> pq = new PriorityQueue<>();
        PriorityQueue<Event> printable = new PriorityQueue<>();

        List<Server> servers = new ArrayList<>();
        List<Double> arrivalTimes = new ArrayList<>();
        List<Customer> customerList = new ArrayList<>(); 
       
        for (int i = 0; i < numOfServers; i++) {
            Server server = new Server(i + 1, true, false, 0, maxQueue);
            servers.add(server);
        }
        
        arrivalTimes.add(0.0);
        for (int i = 1; i < numOfCustomers; i++) {
            double time = arrivalTimes.get(i - 1) + rng.genInterArrivalTime();
            arrivalTimes.add(time);
        }

        for (int i = 0; i < numOfCustomers; i++) {
            Customer customer = new Customer(i + 1, arrivalTimes.get(i), 
                () -> rng.genServiceTime(), rng.genCustomerType() < probabilityGreedy);
            customerList.add(customer);
        }

        for (Customer customer : customerList) {
            pq.offer(new ArriveEvent(customer, servers));
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
                    numServed++;
                    totalWaitingTime += event.getStartTime() - event.getCustomer().getArrivalTime();
                    pq.offer(nextEvent);
                    break;

                case DONE_EVENT:
                    for (int i = 0; i < servers.size(); i++) {
                        if (servers.get(i).getID() == nextEvent.getEventServer().getID()) {
                            eventServer = servers.get(i);
                        }
                    }

                    if (rng.genRandomRest() < probabilityRest) {
                        pq.offer(new ServerRestEvent(nextEvent.getCustomer(), 
                                    nextEvent.getStartTime(), 
                                    servers, 
                                    nextEvent.getEventServer(), 
                                    rng.genRestPeriod()));
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
                        pq.offer(new ServeEvent(nextEvent.getCustomer(), 
                                    nextEvent.getStartTime(), 
                                    servers, 
                                    nextEvent.getEventServer()));
                    }
                    break;
                
                case LEAVE_EVENT: 
                    numLeft++;
                    break;
                
                default:
                    break;
            } 
        }

        for (Event e : printable) {
            System.out.println(e);
        }

        double averageTime = numServed == 0 ? 0.0 : totalWaitingTime / numServed;
        String statistics = String.format("[%.3f %d %d]", averageTime, numServed, numLeft);
        System.out.println(statistics);

    }
}
