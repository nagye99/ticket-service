package com.epam.training.ticketservice;

import com.epam.training.ticketservice.ui.interpreter.CommandLineInterpreter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.epam.training.ticketservice");
        CommandLineInterpreter commandLineInterpreter = context.getBean(CommandLineInterpreter.class);
        commandLineInterpreter.handleUserInputs();
    }
}
