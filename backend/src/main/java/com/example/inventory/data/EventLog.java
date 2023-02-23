package com.example.inventory.data;

import com.mongodb.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("event")
public class EventLog {

    @Id
    private String id;
    private LocalDateTime timestamp;
    private String user;
    @Nullable
    private String reason;
    private String command;
    private Object data;

}
