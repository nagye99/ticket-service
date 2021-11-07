package com.epam.training.ticketservice.ui.command.impl;

public class ListScreeningsCommand extends AbstractCommand{
    public ListScreeningsCommand() {
        super("list", "screenings");
    }

    @Override
    public String process(String[] params) {
        return "Screenings list";
    }
}
