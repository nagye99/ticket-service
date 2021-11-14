package com.epam.training.ticketservice.ui.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UserCommand {

    @ShellMethod(key = "describe account", value = "Describe account")
    public String describeAccount() {
        return "Signed in with privileged account admin";
    }

    @ShellMethod(key = "sign up", value = "Sign up new account")
    public String signUp(String userName, String password){
        return "Sign up " + userName;
    }

    @ShellMethod(key = "sign in", value ="Sign in")
    public String signIn(String userName, String password) {
        return "Sign in with " + userName;
    }


    @ShellMethod(key = "sign in privileged", value = "Sign in with privileged account")
    public String signInPrivileged(String userName, String password) {
        return "Sign in with " + userName;
    }

    @ShellMethod(key = "sign out", value = "Sign out")
    public String signOut() {
        return "Sign Out";
    }
}
