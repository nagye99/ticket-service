package com.epam.training.ticketservice.ui.command.impl;

public class CreateMovieCommand extends AbstractCommand{
    
    public CreateMovieCommand() {
        super("create", "movie");
    }

    @Override
    public String process(String[] params) {
        return params[0]+params[1]+params[2];
    }
}
