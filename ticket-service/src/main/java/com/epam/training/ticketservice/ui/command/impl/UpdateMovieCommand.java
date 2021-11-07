package com.epam.training.ticketservice.ui.command.impl;

public class UpdateMovieCommand extends AbstractCommand{

    public UpdateMovieCommand() {
        super("update", "movie");
    }

    @Override
    public String process(String[] params) {
        return "update movie";
    }
}
