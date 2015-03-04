package org.home.gg;


public class VehicleIdDataBuilder {

    public static final String[] VALID_IDS = new String[]{"ab1254", "cd789"};
    public static final String[] NOT_VALID_IDS = new String[]{""};

    public static VehicleId genVehicleId(){
      return new VehicleId("12345");
    }

    public String getValidString(){
      return VALID_IDS[0];
    }

    public String getOtherThan(String vehicleId){
      for(String id : VALID_IDS){
         if(!id.equals(vehicleId)){
            return id;
         }
      }
      throw new IllegalStateException("could not get other than " +vehicleId);
    }


}
