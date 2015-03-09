package org.home.gg;


import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class BigGarageBuilder {

    private Stack<String> levels = new Stack<String>();
    private Set<ParkingSpot> slots = new HashSet<ParkingSpot>();


    public BigGarageBuilder addLevel(String level){
        if(this.levels.contains(level)){
          throw new IllegalArgumentException(String.format("%s already defined"));
        }else{
           this.levels.push(level);
        }
      return this;
    }

    public BigGarageBuilder addPlace(String place, VehicleSpec vehicleSpec){
        if(this.levels.isEmpty()){
           throw new IllegalStateException("level should be defined before");
        }
       this.slots.add(new ParkingSpot(new SpotLocation(this.levels.peek(), place), vehicleSpec));
      return this;
    }

    public ParkingFacility buildGarage(){
        return new BigGarage(slots);
    }


}
