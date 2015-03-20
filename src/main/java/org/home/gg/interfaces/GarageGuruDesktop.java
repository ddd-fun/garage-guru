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

    // ------- application functions -------------------

    static final BiFunction<VehicleId, VehicleType,  LotLocation> parkFunction = application::tryToPark;

    static final Consumer<VehicleId> cleanFunction = application::cleanParkingLot;

    static final Function<VehicleId, Optional<LotLocation>> finVehicleFunction = application::findParkedVehicleBy;

    static final Supplier<NumberOfFreeLots> getNumberOfFreeLotFunction = application::getAvailableLots;

    static final Function<String, VehicleType> vehicleTypeReader = (String s)-> {

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



    public static void main(String[] args) {

        System.out.println("Enter a command: park, clean, find, free, exit");

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
        }

        if(commandLine.startsWith("park")){
           final String[] commands =  commandLine.split(" ");
           consolePrinter.accept( parkFunction.apply( new VehicleId(commands[1]), vehicleTypeReader.apply(commands[2])) );
        }

        if(commandLine.startsWith("clean")){
            final String[] commands =  commandLine.split(" ");
            cleanFunction.accept(new VehicleId(commands[1]));
            consolePrinter.accept("cleaned" );
        }

        if(commandLine.startsWith("find")){
            final String[] commands =  commandLine.split(" ");
            Optional<LotLocation> maybeLot = finVehicleFunction.apply(new VehicleId(commands[1]));
            if(maybeLot.isPresent()){
              consolePrinter.accept(maybeLot.get());
            } else {
              consolePrinter.accept(String.format("vehicle %s not found", commands[1]));
            }
        }

    }


    private static final Consumer<Object> consolePrinter = System.out::println;




}
