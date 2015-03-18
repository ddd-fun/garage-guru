package org.home.gg.domain.vehicle;


import org.home.gg.domain.common.Reject;


/**
 * This value object represents unique vehicle id, for ex. license plate.
 */
public class VehicleId {

   private static final int MIN_LENGTH = 1;
   private static final int MAX_LENGTH = 64;

   private final String id;


    public VehicleId(String id) {
       Reject.ifBlank(id);
       validateLength(id);
       this.id = id;
    }


    private static void validateLength(String id){
       int length = id.length();
       if(length < MIN_LENGTH || length > MAX_LENGTH){
         throw new IllegalArgumentException(String.format("id length %s is not in range %s - %s", length, MIN_LENGTH, MAX_LENGTH));
       }
    }


    public String getId(){
      return id;
    }


    @Override
    public String toString() {
       return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleId)) return false;

        VehicleId vehicleId = (VehicleId) o;

        if (!id.equals(vehicleId.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
