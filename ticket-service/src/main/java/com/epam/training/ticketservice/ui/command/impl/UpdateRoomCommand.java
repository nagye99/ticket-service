package com.epam.training.ticketservice.ui.command.impl;

public class UpdateRoomCommand extends AbstractCommand{

    public UpdateRoomCommand() {
        super("update", "room");
    }

    @Override
    public String process(String[] params) {
        return "update room";
    }
}
