package org.home.gg;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingApplicationTest {


    private ParkingApplication createParkingApplicationWith(GarageBuilder garageBuilder){
      return new ParkingApplication(garageBuilder.buildGarage());
    }


    @Test
    public void whenThereIsAvailablePlaceThenTicketIsReturned() throws Exception {

        String level = "A";
        String parkingLotId = "1";
        String vehicleId = "AB456H";

        ParkingApplication application = createParkingApplicationWith(
                                     anOneLevelGarage(level)
                                     .withParkingLot(parkingLotId, suitableForCarOnly()));

        assertThat("when there is a place available then parking ticked is returned with parking lot location",
                   application.tryToPark(new VehicleId(vehicleId), VehicleType.CAR),
                   new TicketMatcher(vehicleId, level, parkingLotId));
    }


    @Test(expected = VehicleIsAlreadyParkedException.class)
    public void whenTheVehicleIsAlreadyParkedThenExceptionIsThrown() throws Exception {

        String vehicleId = "AB456H";

        ParkingApplication application = createParkingApplicationWith(
                anOneLevelGarage("A")
                        .withParkingLot("1", suitableForCarOnly()));

        application.tryToPark(new VehicleId(vehicleId), VehicleType.CAR);
        application.tryToPark(new VehicleId(vehicleId), VehicleType.CAR);
    }



    private VehicleSpec suitableForCarOnly() {
      return new VehicleSpecBuilder().aCar();
    }


    private static class TicketMatcher extends BaseMatcher<ParkingTicket>{

        private final String level;
        private final String parkingLotId;
        private final String vehicleId;

        public TicketMatcher(String vehicleId, String level, String parkingLotId) {
            this.level = level;
            this.parkingLotId = parkingLotId;
            this.vehicleId = vehicleId;
        }

        @Override
        public boolean matches(Object item) {
            ParkingTicket ticket = (ParkingTicket) item;
            String ticketAsString = ticket.toString();
           return ticketAsString.contains(vehicleId) &&
                  ticketAsString.contains(level) &&
                  ticketAsString.contains(parkingLotId);
        }

        @Override
        public void describeTo(Description description) {
           description.appendText("<Parking ticket for vehicle ")
                      .appendValue(vehicleId)
                      .appendText(" Location: ")
                      .appendText("level=").appendValue(level)
                      .appendText(", ")
                      .appendText("lot=").appendValue(parkingLotId);
        }

    }


    private GarageBuilder anOneLevelGarage(String level){
       return new GarageBuilder().withLevel(level);
    }



    @Test(expected = OutOfAvailablePlacesException.class)
    public void whenThereIsNoAvailablePlaceThenExceptionIsThrown() throws Exception {

        ParkingApplication application = createParkingApplicationWith(
                           anOneLevelGarage("A")
                             .withParkingLot("1", suitableForCarOnly()));

        application.tryToPark(new VehicleId("AB456H"), VehicleType.CAR);
        application.tryToPark(new VehicleId("VB356G"), VehicleType.CAR);
    }


}