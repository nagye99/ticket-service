package com.epam.training.ticketservice.ui.command.impl;

public class ListRoomsCommand extends AbstractCommand{
    public ListRoomsCommand() {
        super("list", "rooms");
    }

    @Override
    public String process(String[] params) {
        return "Rooms list";
    }
}
