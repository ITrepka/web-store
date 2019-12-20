package pl.pretkejshop.webstore.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserInvalidDataException extends Exception{
    public UserInvalidDataException(String message) {
        super(message);
    }
}
