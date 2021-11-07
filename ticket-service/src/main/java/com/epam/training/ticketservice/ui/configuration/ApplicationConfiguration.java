package com.epam.training.ticketservice.ui.configuration;

import java.util.Set;

import com.epam.training.ticketservice.ui.command.impl.*;
import com.epam.training.ticketservice.ui.interpreter.CommandLineInterpreter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CommandLineInterpreter commandLineInterpreter(Set<AbstractCommand> abstractCommands) {
        return new CommandLineInterpreter(System.in, System.out, abstractCommands);
    }

    @Bean
    public AbstractCommand signInCommand(){
        return new SignInCommand();
    }

    @Bean
    public  AbstractCommand signOutCommand(){
        return new SignOutCommand();
    }

    @Bean
    public AbstractCommand describeAccountCommand(){
        return new DescribeAccountCommand();
    }

    @Bean
    public AbstractCommand createMovieCommand(){
        return new CreateMovieCommand();
    }

    @Bean
    public AbstractCommand updateMovieCommand(){
        return new UpdateMovieCommand();
    }

    @Bean
    public AbstractCommand deleteMovieCommand(){
        return new DeleteMovieCommand();
    }

    @Bean
    public AbstractCommand listMoviesCommand(){
        return new ListMoviesCommand();
    }

    @Bean
    public AbstractCommand createRoomCommand(){
        return new CreateRoomCommand();
    }

    @Bean
    public AbstractCommand updateRoomCommand(){
        return new UpdateRoomCommand();
    }

    @Bean
    public AbstractCommand deleteRoomCommand(){
        return new DeleteRoomCommand();
    }

    @Bean
    public AbstractCommand listRoomsCommand(){
        return new ListRoomsCommand();
    }

    @Bean
    public AbstractCommand createScreeningCommand(){
        return new CreateScreeningCommand();
    }

    @Bean
    public AbstractCommand deleteScreeningCommand(){
        return new DeleteScreeningCommand();
    }

    @Bean
    public AbstractCommand listScreeningsCommand(){
        return new ListScreeningsCommand();
    }
}
