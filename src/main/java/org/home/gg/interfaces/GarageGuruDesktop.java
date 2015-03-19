package org.home.gg.interfaces;

import org.home.gg.application.ParkingApplication;
import org.home.gg.domain.garage.*;
import org.home.gg.domain.vehicle.VehicleType;
import org.home.gg.infrastructure.GarageInMemoryImpl;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class GarageGuruDesktop {


    private static final Function<Garage, ParkingApplication> singleThreadedAppBuilder = ParkingApplication::new;

    private static final Function<Supplier<Set<ParkingLot>>, Garage> inMemoryGarageBuilder =
            (Supplier<Set<ParkingLot>> lotConfigurator)-> new GarageInMemoryImpl(lotConfigurator.get());

    private static final Supplier<Set<ParkingLot>> twoLevelGarageConfiguration = () -> {

        BiFunction<LotLocation, EnumSet, ParkingLot> lotBuilder = (LotLocation lotLocation, EnumSet supportedTypes)
                -> new ParkingLot(lotLocation, new TypeBasedVehicleSpec(supportedTypes));

        Set<ParkingLot> lotSet = new HashSet<>();
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
          exit();
        }

        if(commandLine.startsWith("free")){
          printFreeLots();
        }

    }


    private static void printFreeLots(){
       NumberOfFreeLots lots = application.getAvailableLots();
       System.out.println(lots);
    }



    private static void exit(){
        System.out.println("Good bye...");
       System.exit(1);
    }




}
