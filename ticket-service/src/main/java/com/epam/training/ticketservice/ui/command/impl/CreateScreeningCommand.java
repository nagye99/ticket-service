package com.epam.training.ticketservice.ui.command.impl;

public class CreateScreeningCommand extends AbstractCommand{

    public CreateScreeningCommand() {
        super("create", "screening");
    }

    @Override
    public String process(String[] params) {
        return "Új vetítés";
    }
}
