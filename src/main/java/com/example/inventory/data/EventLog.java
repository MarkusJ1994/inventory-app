package com.example.inventory.data;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("event")
public class EventLog {

    @Id
    private UUID id;
    private LocalDateTime timestamp;
    private String user;
    @Nullable
    private String reason;
    private String command;
    private Object data;

}
