package br.com.sysmap.bootcamp.infra.exceptions;

public class ExistingUser extends RuntimeException{
    public ExistingUser(){
        super("User existing");
    }

    public ExistingUser(String message){
        super(message);
    }
}
