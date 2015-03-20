package org.home.gg.interfaces;

import org.home.gg.application.ParkingApplication;
import org.home.gg.domain.garage.*;
import org.home.gg.domain.vehicle.VehicleId;
import org.home.gg.domain.vehicle.VehicleType;
import org.home.gg.infrastructure.GarageInMemoryImpl;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GarageGuruDesktop {


    private static final Function<Garage, ParkingApplication> singleThreadedAppBuilder = ParkingApplication::new;

    private static final Function<Supplier<Set<ParkingLot>>, Garage> inMemoryGarageBuilder =
            (Supplier<Set<ParkingLot>> lotConfigurator)-> new GarageInMemoryImpl(lotConfigurator.get());

    private static final Supplier<Set<ParkingLot>> twoLevelGarageConfiguration = () -> {

        BiFunction<LotLocation, EnumSet, ParkingLot> lotBuilder = (LotLocation lotLocation, EnumSet supportedTypes)
                -> new ParkingLot(lotLocation, new TypeBasedVehicleSpec(supportedTypes));

        Set<ParkingLot> lotSet = new LinkedHashSet<>();
        //first level for CAR
        lotSet.add(lotBuilder.apply(new LotLocation("A", "1"), EnumSet.of(VehicleType.CAR)));
        lotSet.add(lotBuilder.apply(new LotLocation("A", "2"), EnumSet.of(VehicleType.CAR)));
        lotSet.add(lotBuilder.apply(new LotLocation("A", "3"), EnumSet.of(VehicleType.CAR)));
        lotSet.add(lotBuilder.apply(new LotLocation("A", "4"), EnumSet.of(VehicleType.CAR)));
        //second level for MOTORBIKE
        lotSet.add(lotBuilder.apply(new LotLocation("B", "1"), EnumSet.of(VehicleType.MOTORBIKE)));
        lotSet.add(lotBuilder.apply(new LotLocation("B", "2"), EnumSet.of(VehicleType.MOTORBIKE)));
        lotSet.add(lotBuilder.apply(new LotLocation("B", "3"), EnumSet.of(VehicleType.MOTORBIKE)));
        lotSet.add(lotBuilder.apply(new LotLocation("B", "4"), EnumSet.of(VehicleType.MOTORBIKE)));
      return lotSet;
    };

    private static final ParkingApplication application =
            singleThreadedAppBuilder.compose(inMemoryGarageBuilder).apply(twoLevelGarageConfiguration);

    // ------- just for fun let use functions -------------------

    static final BiFunction<VehicleId, VehicleType,  LotLocation> parkFunction = application::tryToPark;

    static final Consumer<VehicleId> cleanFunction = application::cleanParkingLot;

    static final Function<VehicleId, Optional<LotLocation>> finVehicleFunction = application::findParkedVehicleBy;

    static final Supplier<NumberOfFreeLots> getNumberOfFreeLotFunction = application::getAvailableLots;


    public static void main(String[] args) {

        System.out.println(getWelcomeMessage());

        Scanner scan = new Scanner(System.in);
        do{
            try{
                processCommand(scan.nextLine());
            }catch(Throwable e){
                System.err.println("Exception: "+e.getMessage());
            }
        }while(true);

    }

    private static void processCommand(String commandLine){
        if(commandLine.startsWith("exit")){
           consolePrinter.accept( "Good bye...");
           System.exit(1);
        }

        if(commandLine.startsWith("free")){
           consolePrinter.accept(getNumberOfFreeLotFunction.get());
          return;
        }

        final String commands[]  =  commandLine.split(" ");

        if(commandLine.startsWith("park")){
          consolePrinter.accept(String.format("your vehicle parked on %s",  parkFunction.apply( new VehicleId(commands[1]), vehicleTypeReader.apply(commands[2])) ) );
         return;
        }

        if(commandLine.startsWith("clean")){
            cleanFunction.accept(new VehicleId(commands[1]));
            consolePrinter.accept("cleaned" );
           return;
        }

        if(commandLine.startsWith("find")){
            Optional<LotLocation> maybeLot = finVehicleFunction.apply(new VehicleId(commands[1]));
            if(maybeLot.isPresent()){
              consolePrinter.accept(maybeLot.get());
            } else {
              consolePrinter.accept(String.format("vehicle %s not found", commands[1]));
            }
          return;
        }

        System.err.println(String.format("Unknown command %s", commandLine));
    }


    private static String getWelcomeMessage() {
        StringBuilder builder = new StringBuilder("Welcome to GarageGuru command line. Please, enter a command:");
        builder.append("\n");
        builder.append("- for getting number of available lots, type: free");
        builder.append("\n");
        builder.append("- for parking your vehicle, type: park <vehicle id> <vehicle type>, for ex. park AH895JK CAR");
        builder.append("\n");
        builder.append("- for find your vehicle, type: find <vehicle id>, for ex. find AH895JK");
        builder.append("\n");
        builder.append("- for cleaning parking lot, type: clean <vehicle id>, for ex. clean AH895JK");
        builder.append("\n");
        builder.append("- if you get annoyed, type: exit");
        builder.append("\n");
        builder.append("---------------------------------------------------------------------------");
       return builder.toString();
    }


    private static final Function<String, VehicleType> vehicleTypeReader = (String s)-> {

        if(s == null || s.trim().length() == 0){
            throw new IllegalArgumentException("vehicle type is null");
        }

        Optional<String> maybeType = Arrays.asList(VehicleType.values()).stream()
                .map(VehicleType::name).map(String::toUpperCase).filter((String type) -> type.startsWith(s.toUpperCase())).findFirst();

        if(maybeType.isPresent()){
            return VehicleType.valueOf(maybeType.get());
        }else{
            throw new IllegalArgumentException(String.format("%s is unknown vehicle type", s));
        }

    };


    private static final Consumer<Object> consolePrinter = System.out::println;




}
