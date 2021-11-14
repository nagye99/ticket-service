package com.epam.training.ticketservice.ui.command;

import org.springframework.shell.standard.ShellMethod;

public class PriceCommand {

    @ShellMethod(key = "update base price", value = "Updating base price")
    public String updateBasePrice(int price){
        return "Base price updated to: " + price;
    }

    @ShellMethod(key = "create price component", value = "Creating a new price component")
    public String createPriceComponent(String componentName, int price){
        return componentName + " " + price;
    }

    @ShellMethod(key = "attach price component to room", value = "Price component attach to room")
    public String addPriceComponentToRoom(String componentName, String roomName){
        return "Component to room";
    }

    @ShellMethod(key = "attach price component to movie", value = "Price component attach to movie")
    public String addPriceComponentToMovie(String componentName, String movieName){
        return "Component to room";
    }

    @ShellMethod(key = "attach price component to screening", value = "Price component attach to screening")
    public String addPriceComponentToRoom(String componentName, String movieName, String roomName, String date){
        return "Component to screening";
    }
}
