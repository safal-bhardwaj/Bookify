package org.safal.bookify.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class WrongPasswordException extends RuntimeException{

  public  WrongPasswordException(String message)
    {
        super(message);
    }
    
}
