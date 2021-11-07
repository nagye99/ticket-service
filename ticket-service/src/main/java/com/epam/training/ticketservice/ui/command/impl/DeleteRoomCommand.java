package com.epam.training.ticketservice.ui.command.impl;

public class DeleteRoomCommand extends AbstractCommand{
    public DeleteRoomCommand() {
        super("delete", "room");
    }

    @Override
    public String process(String[] params) {
        return "delete room";
    }
}
