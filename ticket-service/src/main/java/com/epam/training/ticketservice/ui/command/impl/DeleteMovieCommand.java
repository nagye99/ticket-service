package com.epam.training.ticketservice.ui.command.impl;

public class DeleteMovieCommand extends AbstractCommand{
    public DeleteMovieCommand() {
        super("delete", "movie");
    }

    @Override
    public String process(String[] params) {
        return "delete movie";
    }
}
