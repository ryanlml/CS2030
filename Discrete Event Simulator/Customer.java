package cs2030.simulator;

import java.util.function.Supplier;

/**
 * A Customer class encapsulates the data related to a customer.
 */
public class Customer {

    private final int customerID;
    private final double arrivalTime;
    private final Supplier<Double> serviceTime;
    private final boolean isGreedy;

    /**
     * Constructor for a customer.
     * @param customerID ID associated to the customer.
     * @param arrivalTime Time of arrival of the customer.
     * @param serviceTime Supplier for the time needed to serve the customer.
     * @param isGreedy Whether the customer is greedy or not.
     */
    public Customer(int customerID, 
            double arrivalTime, 
            Supplier<Double> serviceTime, 
            boolean isGreedy) {
        
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.isGreedy = isGreedy;
    }

    /** 
     * Constructor for a non-greedy customer with a default service time.
     * @param customerID ID associated to the customer.
     * @param arrivalTime Time of arrival of the customer.
     */
    public Customer(int customerID, double arrivalTime) {
        this(customerID, arrivalTime, () -> 1.0, false);
    }

    /**
     * Constructor for a non-greedy customer.
     * @param customerID ID associated to the customer.
     * @param arrivalTime Time of arrival of the customer.
     * @param serviceTime Supplier for the time needed to serve the customer.
     */
    public Customer(int customerID, double arrivalTime, Supplier<Double> serviceTime) {
        this(customerID, arrivalTime, serviceTime, false);
    }

    public int getID() {
        return this.customerID;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public double getServiceTime() {
        return this.serviceTime.get();
    }

    public boolean isGreedy() {
        return this.isGreedy;
    }
 
}
