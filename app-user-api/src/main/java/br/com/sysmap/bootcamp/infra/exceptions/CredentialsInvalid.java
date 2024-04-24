package br.com.sysmap.bootcamp.infra.exceptions;

public class CredentialsInvalid extends RuntimeException{
    public CredentialsInvalid(){
        super("User not found");
    }

    public CredentialsInvalid(String message){
        super(message);
    }
}
