package pl.pretkejshop.webstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pretkejshop.webstore.service.dto.CreateUpdateMessageDto;
import pl.pretkejshop.webstore.service.dto.MessageDto;
import pl.pretkejshop.webstore.service.exception.AlreadyExistsException;
import pl.pretkejshop.webstore.service.exception.InvalidDataException;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping
    public List<MessageDto> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/{id}")
    public MessageDto getMessageById(@PathVariable int id) throws NotFoundException {
        return messageService.getMessageById(id);
    }

    @PostMapping
    public MessageDto addNewMessage(@RequestBody CreateUpdateMessageDto createMessageDto) throws AlreadyExistsException, InvalidDataException, NotFoundException {
        return messageService.addNewMessage(createMessageDto);
    }

    @PutMapping("/{id}")
    public MessageDto updateMessageById(@PathVariable int id, @RequestBody CreateUpdateMessageDto messageToUpdate) throws NotFoundException, InvalidDataException {
        return messageService.updateMessageById(id, messageToUpdate);
    }

    @DeleteMapping("/{id}")
    public MessageDto deleteMessageById(@PathVariable int id) throws NotFoundException {
        return messageService.deleteMessageById(id);
    }
}
