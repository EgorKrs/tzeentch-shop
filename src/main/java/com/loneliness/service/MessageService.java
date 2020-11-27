package com.loneliness.service;

import com.loneliness.entity.domain.Message;
import com.loneliness.entity.domain.News;
import com.loneliness.entity.domain.Review;
import com.loneliness.repository.MessageRepository;
import com.loneliness.repository.NewsRepository;
import com.loneliness.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MessageService extends CRUDService<Message>{
    public MessageService(MessageRepository repository, SearchService searchService){
        this.repository = repository;
        this.searchService = searchService;
    }
    public Optional<List<Message>> findAllByRoomTitleOrderByDateDateDesc(String title){
//        return Optional.of(((MessageRepository)repository).findAllByRoomTitleOrderByDateDateDesc(title));
//        Optional<List<Message>> optionalMessage  = ((MessageRepository)repository).getAllByIdIsNotNullOrderByDateDesc();
        Optional<List<Message>> optionalMessage  = ((MessageRepository)repository).getAllByIdIsNotNullOrderByDateDesc();
        optionalMessage.ifPresent(messages -> messages.removeIf(message -> !message.getRoom().getTitle().equals(title)));
        return optionalMessage;

    }
}
