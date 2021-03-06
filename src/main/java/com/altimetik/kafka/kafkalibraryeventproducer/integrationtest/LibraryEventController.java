package com.altimetik.kafka.kafkalibraryeventproducer.integrationtest;

import com.altimetik.kafka.kafkalibraryeventproducer.domain.LibraryEvent;
import com.altimetik.kafka.kafkalibraryeventproducer.domain.LibraryEventType;
import com.altimetik.kafka.kafkalibraryeventproducer.producer.unittest.LibraryEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
public class LibraryEventController {

    @Autowired
    private LibraryEventProducer eventProducer;

    @PostMapping("/v/libraryEvent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException, InterruptedException, ExecutionException, TimeoutException {
        log.info("Before message LibraryEvent Sent");
            //Non synchronous way
        //eventProducer.sendLibraryEvent(libraryEvent);

            //Synchronous way
       /* SendResult<Integer,String> sendResult=eventProducer.sendLibraryEventSynchronous(libraryEvent);
        log.info("Send result is {}",sendResult.toString());*/

           //Using Custom topic
        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        eventProducer.sendLibraryEvent_Approach2( libraryEvent);

        log.info("After message LibraryEvent Sent");
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PutMapping("/v/libraryEvent")
    public ResponseEntity<?> putLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException, InterruptedException, ExecutionException, TimeoutException {
        log.info("Before message LibraryEvent Sent");

        if(libraryEvent.getLibraryEventId()==null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter valid library event id");
        }

        libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
        eventProducer.sendLibraryEvent_Approach2( libraryEvent);
        log.info("After message LibraryEvent Sent");
        return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
    }
}
