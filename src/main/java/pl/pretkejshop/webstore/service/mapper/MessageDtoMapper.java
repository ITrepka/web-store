package pl.pretkejshop.webstore.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pretkejshop.webstore.model.Message;
import pl.pretkejshop.webstore.model.User;
import pl.pretkejshop.webstore.repository.MessageRepository;
import pl.pretkejshop.webstore.repository.UserRepository;
import pl.pretkejshop.webstore.service.dto.CreateUpdateMessageDto;
import pl.pretkejshop.webstore.service.dto.MessageDto;
import pl.pretkejshop.webstore.service.exception.NotFoundException;

@Service
public class MessageDtoMapper {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public MessageDto toDto(Message message) {
        Integer userFromId = message.getUserFrom() == null ? null : message.getUserFrom().getId();
        return MessageDto.builder()
                .id(message.getId())
                .subject(message.getSubject())
                .text(message.getText())
                .createdAt(message.getCreatedAt())
                .updatedAt(message.getUpdatedAt())
                .userFromId(userFromId)
                .build();
    }

    public Message toModel(CreateUpdateMessageDto createMessageDto) throws NotFoundException {
        Integer userFromId = createMessageDto.getUserFromId();
        User userFrom = userFromId == null ? null :
                userRepository.findById(userFromId)
                        .orElseThrow(() -> new NotFoundException("Not found user with id =" + userFromId));
        return Message.builder()
                .id(null)
                .subject(createMessageDto.getSubject())
                .createdAt(null)
                .updatedAt(null)
                .text(createMessageDto.getText())
                .userFrom(userFrom)
                .build();
    }
}
