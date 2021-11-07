package com.epam.training.ticketservice.ui.command.impl;

public class CreateRoomCommand extends AbstractCommand{

    public CreateRoomCommand() {
        super("create", "room");
    }

    @Override
    public String process(String[] params) {
        return "Új room";
    }
}
