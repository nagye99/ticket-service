package com.epam.training.ticketservice.ui.command.impl;

public class DeleteScreeningCommand extends AbstractCommand{
    public DeleteScreeningCommand() {
        super("delete", "screening");
    }

    @Override
    public String process(String[] params) {
        return "delete screening";
    }
}
