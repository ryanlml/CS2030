package cs2030.simulator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A server class encapsulated the data related to a server.
 */
public class Server {

    private final int serverID;
    private final boolean isAvailable;
    private final boolean hasWaitingCustomer;
    private final double nextAvailableTime;
    private final int maxQueue;
    private final Queue<Customer> queue;

    /**
     * Constructor for a server.
     * @param serverID ID associated to the server.
     * @param isAvailable Whether the server is available to server a customer.
     * @param hasWaitingCustomer Whether the server has a waiting customer.
     * @param nextAvailableTime The next available time of the server.
     * @param maxQueue Maximum queue length of the server.
     * @param queue Queue of the server.
     */
    public Server(int serverID, 
            boolean isAvailable, 
            boolean hasWaitingCustomer, 
            double nextAvailableTime, 
            int maxQueue, 
            Queue<Customer> queue) {
        
        this.serverID = serverID;
        this.isAvailable = isAvailable;
        this.hasWaitingCustomer = hasWaitingCustomer;
        this.nextAvailableTime = nextAvailableTime;
        this.maxQueue = maxQueue;
        this.queue = queue;
    }

    /**
     * Constructor for a server with an empty queue, allowing a maximum of 1 customer in the queue.
     * @param serverID ID associated to the server.
     * @param isAvailable Whether the server is available to server a customer.
     * @param hasWaitingCustomer Whether the server has a waiting customer.
     * @param nextAvailableTime The next available time of the server.
     */
    public Server(int serverID, 
            boolean isAvailable, 
            boolean hasWaitingCustomer, 
            double nextAvailableTime) {
        
        this(serverID, 
                isAvailable, 
                hasWaitingCustomer, 
                nextAvailableTime, 
                1, 
                new LinkedList<>());
    }

    /**
     * Constructor for a server with an empty queue.
     * @param serverID ID associated to the server.
     * @param isAvailable Whether the server is available to server a customer.
     * @param hasWaitingCustomer Whether the server has a waiting customer.
     * @param nextAvailableTime The next available time of the server.
     * @param maxQueue Maximum queue length of the server.
     */
    public Server(int serverID, 
            boolean isAvailable, 
            boolean hasWaitingCustomer, 
            double nextAvailableTime, 
            int maxQueue) {
        
        this(serverID, 
                isAvailable, 
                hasWaitingCustomer, 
                nextAvailableTime, 
                maxQueue, 
                new LinkedList<>());
    }
    
    /**
     * Constructor for a server with a specified queue length.
     * @param serverID ID associated to the server.
     * @param maxQueue Maximum queue length of the server.
     */
    public Server(int serverID, 
            int maxQueue) {
        
        this(serverID, 
                true, 
                false, 
                0, 
                maxQueue, 
                new LinkedList<>());
    }

    /**
     * Default constructor for a server.
     * @param serverID ID associated to the server.
     */
    public Server(int serverID) {

        this(serverID, 
                true, 
                false, 
                0, 
                1, 
                new LinkedList<>());
    }

    /**
     * Checks whether there is space in the queue for a customer.
     * @return boolean of whether the server is queueable.
     */
    public boolean isQueueable() {
        return this.queue.size() < this.maxQueue;
    }

    /**
     * Checks whether the queue is empty.
     * @return boolean of whether a customer is present in the queue.
     */
    public boolean isAnyoneInQueue() {
        return this.queue.size() > 0;
    }

    /**
     * Adds the customer to the queue.
     * @param customer customer to be added.
     * @return Server with the added customer.
     */
    public Server addToQueue(Customer customer) {
        
        Queue<Customer> newQueue = new LinkedList<>(this.queue);
        newQueue.offer(customer);
        
        return new Server(this.serverID, 
                this.isAvailable, 
                this.hasWaitingCustomer, 
                this.nextAvailableTime, 
                this.maxQueue, 
                newQueue);
    }
    
    /**
     * Retrieves, but does not remove, the next customer of the queue.
     * @return Customer next in queue.
     */
    public Customer peek() {
        return this.queue.peek();
    }

    /**
     * Retrieves and removes the next customer of the queue.
     * @return Customer next in queue.
     */
    public Customer poll() {
        return this.queue.poll();
    }

    public int getID() {
        return this.serverID;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public boolean hasWaitingCustomer() {
        return this.hasWaitingCustomer;
    }

    public double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    public int getMaxQueue() {
        return this.maxQueue;
    }

    public Queue<Customer> getQueue() {
        return this.queue;
    } 

}
