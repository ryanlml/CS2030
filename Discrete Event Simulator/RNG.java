package cs2030.simulator;

import java.util.Random;

public class RNG {
    
    private final RandomGenerator rng;

    public RNG(int seed, double lambda, double mu, double rho) {
        this.rng = new RandomGenerator(seed, lambda, mu, rho);
    }

    public double genCustomerType() {
        return this.rng.genCustomerType();
    }

    public double genInterArrivalTime() {
        return this.rng.genInterArrivalTime();
    }

    public double genRandomRest() {
        return this.rng.genRandomRest();
    }

    public double genRestPeriod() {
        return this.rng.genRestPeriod();
    }

    public double genServiceTime() {
        return this.rng.genServiceTime();
    }

}
