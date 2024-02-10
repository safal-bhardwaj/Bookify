package org.safal.bookify.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserAlreadyExistException extends RuntimeException 
{
   public UserAlreadyExistException(String message)
    {
        super(message);
    }
    
}
