package com.epam.training.ticketservice.ui.command.impl;

public class SignInCommand extends AbstractCommand{
    public SignInCommand() {
        super("sign", "in");
    }

    @Override
    public String process(String[] params) {
        return "Sign in";
    }
}
