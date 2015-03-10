package org.home.gg;


import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GarageBuilder {

    private Stack<String> levels = new Stack<String>();
    private Set<ParkingLot> slots = new HashSet<ParkingLot>();


    public GarageBuilder withLevel(String level){
        if(this.levels.contains(level)){
          throw new IllegalArgumentException(String.format("%s already defined"));
        }else{
           this.levels.push(level);
        }
      return this;
    }

    public GarageBuilder withParkingLot(String place, EnumSet<VehicleType> supportedTypes){
        if(this.levels.isEmpty()){
           throw new IllegalStateException("level should be defined before");
        }
       this.slots.add(new ParkingLot(new LotLocation(this.levels.peek(), place), supportedTypes));
      return this;
    }

    public ParkingFacility buildGarage(){
        return new BigGarage(slots);
    }


}
