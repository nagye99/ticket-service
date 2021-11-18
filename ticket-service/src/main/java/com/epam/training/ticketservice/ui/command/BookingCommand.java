package com.epam.training.ticketservice.ui.command;

import org.springframework.shell.standard.ShellMethod;

import java.util.Date;

public class BookingCommand {

    @ShellMethod(key ="book", value = "Booking seat(s) to a screening")
    public String book(String movieName, String roomName, Date date, String seats){
        return movieName;
    }

    @ShellMethod(key ="show price", value = "Show price seat(s) to a screening")
    public String showPrice(String movieName, String roomName, Date date, String seats){
        return movieName;
    }
}
