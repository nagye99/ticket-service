package com.epam.training.ticketservice.ui.command.impl;

public class BaseCommand extends AbstractCommand {

    public BaseCommand(String action, String entityType) {
        super(action, entityType);
    }

    @Override
    public String process(String[] params) {
        throw new UnsupportedOperationException();
    }
}
