package com.epam.training.ticketservice.ui.command.impl;

public class DescribeAccountCommand extends AbstractCommand{
    public DescribeAccountCommand() {
        super("describe", "account");
    }

    @Override
    public String process(String[] params) {
        return "admin";
    }
}
