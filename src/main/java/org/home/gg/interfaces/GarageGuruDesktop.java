package org.home.gg.interfaces;

import org.home.gg.application.ParkingApplication;
import org.home.gg.domain.garage.*;
import org.home.gg.domain.vehicle.VehicleType;
import org.home.gg.infrastructure.GarageInMemoryImpl;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Supplier;

public class GarageGuruDesktop {


    private static ParkingApplication application;


    private static ParkingApplication createApplication(){
       return null;
    }


    public static void main(String[] args) {

        application = createApplication();

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
