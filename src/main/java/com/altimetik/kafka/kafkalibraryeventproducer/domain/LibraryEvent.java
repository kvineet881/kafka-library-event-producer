package com.altimetik.kafka.kafkalibraryeventproducer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibraryEvent {
    private Integer libraryEventId;
    private LibraryEventType libraryEventType;
    private Book book;
}