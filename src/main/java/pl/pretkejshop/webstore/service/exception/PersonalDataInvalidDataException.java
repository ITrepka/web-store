package pl.pretkejshop.webstore.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonalDataInvalidDataException extends Exception {
    public PersonalDataInvalidDataException(String message) {
        super(message);
    }
}
