package com.example.streamdemo.Controller;


import com.example.streamdemo.Models.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxExtensionsKt;

import java.io.IOException;
import java.time.Duration;

@RestController
@RequestMapping("/api")
public class APIController {

    @GetMapping(value="/data")
    public ResponseEntity<StreamingResponseBody> streamData() throws IOException {
        StreamingResponseBody responseBody = response -> {
            for (int i = 1; i <= 1000; i++) {
                try {
                    Thread.sleep(10);
                    response.write(("Data stream line - " + i + "\n").getBytes());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseBody);
    }
    @GetMapping(value = "/hello")
    public ResponseEntity<?> hellWorld() throws IOException {
        System.out.println("hello");

        return new ResponseEntity<>("hello world", HttpStatus.OK);
    }

    @GetMapping(value = "/data/flux", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Object> streamDataFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(i -> "Data stream line - " + i );
    }

    @GetMapping("/json")
    public ResponseEntity<StreamingResponseBody> streamJson() {
        int maxRecords = 1000;
        StreamingResponseBody responseBody = response -> {
            for (int i = 1; i <= maxRecords; i++) {
                Student st = new Student("Name" + i, i);
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(st) +"\n";
                response.write(jsonString.getBytes());
                response.flush();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(responseBody);
    }

}
