package com.epam.training.ticketservice.ui.command.impl;

public class SignOutCommand extends AbstractCommand{
    public SignOutCommand() {
        super("sign", "out");
    }

    @Override
    public String process(String[] params) {
        return "Sign Out";
    }
}
