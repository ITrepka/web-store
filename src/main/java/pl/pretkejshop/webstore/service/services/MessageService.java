package pl.pretkejshop.webstore.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Message;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.MessageRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateMessageDto;
import pl.pretkejshop.webstore.service.dto.MessageDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;
import pl.pretkejshop.webstore.service.mapper.MessageDtoMapper;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageDtoMapper messageDtoMapper;
    @Autowired
    private UserRepository userRepository;

    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(message -> messageDtoMapper.toDto(message))
                .collect(Collectors.toList());
    }

    @Transactional
    public MessageDto getMessageById(int id) throws NotFoundException {
        return messageRepository.findById(id)
                .map(message -> messageDtoMapper.toDto(message))
                .orElseThrow(() -> new NotFoundException("Not found message with id = " + id));
    }

    @Transactional
    public MessageDto addNewMessage(CreateUpdateMessageDto createMessageDto) throws NotFoundException {
        Message message = messageDtoMapper.toModel(createMessageDto);
        message.setCreatedAt(OffsetDateTime.now());
        Message savedMessage = messageRepository.save(message);
        return messageDtoMapper.toDto(savedMessage);
    }

    @Transactional
    public MessageDto updateMessageById(int id, CreateUpdateMessageDto messageToUpdate) throws NotFoundException {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not Found Message with id = " + id));

        Integer userFromId = messageToUpdate.getUserFromId();
        User userFrom = userFromId == null ? null :
                userRepository.findById(userFromId)
                        .orElseThrow(() -> new NotFoundException("Not found user with id =" + userFromId));

        message.setUserFrom(userFrom);
        message.setText(messageToUpdate.getText());
        message.setUpdatedAt(OffsetDateTime.now());
        Message savedMessage = messageRepository.save(message);
        return messageDtoMapper.toDto(savedMessage);
    }

    @Transactional
    public MessageDto deleteMessageById(int id) throws NotFoundException {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found message with id = " + id));
        messageRepository.deleteById(id);
        return messageDtoMapper.toDto(message);
    }
}
