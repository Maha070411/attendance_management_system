package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummaryDTO {

    private long present;
    private long late;
    private long halfDay;
    private double totalHours;
}