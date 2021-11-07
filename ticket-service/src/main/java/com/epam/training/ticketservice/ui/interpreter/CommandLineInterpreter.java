package com.epam.training.ticketservice.ui.interpreter;

import com.epam.training.ticketservice.ui.command.impl.AbstractCommand;
import com.epam.training.ticketservice.ui.command.impl.BaseCommand;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class CommandLineInterpreter {

    private final Scanner scanner;
    private final OutputStream outputStream;
    private final Set<AbstractCommand> commandSet;

    public CommandLineInterpreter(InputStream inputStream, OutputStream outputStream, Set<AbstractCommand> commandSet) {
        this.scanner = new Scanner(inputStream);
        this.outputStream = outputStream;
        this.commandSet = commandSet;
    }

    public void handleUserInputs() throws IOException {
        outputStream.write("Ticket service>".getBytes());
        String inputLine;
        while (!"exit".equals(inputLine = scanner.nextLine())) {
            String[] commandParts = inputLine.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
            if (commandParts.length < 2) {
                throw new IllegalArgumentException("You must provide at least 2 parameters!");
            }
            Optional<AbstractCommand> command = getCommandFromParts(commandParts);
            if (command.isEmpty()) {
                throw new IllegalArgumentException("You have provided an invalid command: " + inputLine);
            } else {
                outputStream.write(command.get().process(inputLine).getBytes());
                outputStream.write(System.lineSeparator().getBytes());
                outputStream.write("Ticket service>".getBytes());
            }
        }
    }

    private Optional<AbstractCommand> getCommandFromParts(String[] commandParts) {
        AbstractCommand abstractCommand = new BaseCommand(commandParts[0], commandParts[1]);
        return commandSet.stream().filter(command -> command.equals(abstractCommand)).findFirst();
    }
}
