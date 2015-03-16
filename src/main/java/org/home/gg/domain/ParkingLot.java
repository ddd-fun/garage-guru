package org.home.gg.domain;


import org.home.gg.domain.common.Validators;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParkingLot {

    private final LotLocation location;
    private final VehicleSpec supportedVehiclesSpec;
    private VehicleId parkedVehicle;

    /**
     * This implementation uses locks to control state modifications.
     * Having both types of locks improves performance during iterative reads when looking for suitable lot.
     */
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock read  = readWriteLock.readLock();
    private final Lock write = readWriteLock.writeLock();

    public ParkingLot(LotLocation location, VehicleSpec supportedVehiclesSpec) {
      Validators.assertArgIsNotNull(location);
      Validators.assertArgIsNotNull(supportedVehiclesSpec);
      this.location = location;
      this.supportedVehiclesSpec = supportedVehiclesSpec;
    }


    public void parkVehicle(VehicleId vehicleId, VehicleType vehicleType){
        if(!this.supportedVehiclesSpec.isSatisfiedBy(vehicleType)){
           throw new IllegalArgumentException(String.format("%s is not supported by %s", vehicleType, supportedVehiclesSpec));
        }

        /**
         * state violation exceptions have different meaning in comparing with non-concurrent solution,
         * here it indicates not api misuse but race conditions under concurrent modifications, so might be handled differently:
         * find another lot if this became busy.
         */

        write.lock();
        try{
            if(isFree()){
                this.parkedVehicle = vehicleId;
            } else {
               throw new IllegalStateException(String.format("%s is already reserved by vehicle %s", this, vehicleId));
            }
        }finally {
            write.unlock();
        }


    }

    public boolean supports(VehicleType vehicleType){
      return this.supportedVehiclesSpec.isSatisfiedBy(vehicleType);
    }

    public boolean isFree(){
        read.lock();
        try {
            return parkedVehicle == null;
        }finally {
          read.unlock();
        }
    }

    public VehicleId getParkedVehicle() {
       read.lock();
        try {
            return parkedVehicle;
        }finally {
          read.unlock();
        }

    }

    public void release(VehicleId vehicleId) {
        write.lock();
        try {
            if(!isFree()){
                if( this.parkedVehicle.equals(vehicleId) ){
                    this.parkedVehicle = null;
                } else {
                    throw new IllegalStateException(String.format("%s is not occupied by %s", vehicleId, location));
                }
            }else {
               throw new IllegalStateException(String.format("%s is free", this));
            }
        }finally {
            write.unlock();
        }

    }

    public LotLocation getLocation() {
      return this.location;
    }

    @Override
    public String toString() {
        return String.format("ParkingLot: %s, suitable vehicle: %s", location, this.supportedVehiclesSpec);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingLot)) return false;

        ParkingLot that = (ParkingLot) o;

        if (!location.equals(that.location)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
