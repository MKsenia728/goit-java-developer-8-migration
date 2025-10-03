package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WorkerAge {
    private TypeAge type;
    private String name;
    private LocalDate birthday;
}
