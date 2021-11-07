package com.epam.training.ticketservice.ui.command.impl;

public class ListMoviesCommand extends AbstractCommand{
    public ListMoviesCommand() {
        super("list", "movies");
    }

    @Override
    public String process(String[] params) {
        return "Movies list";
    }
}
