package com.accesa.price_comparator.dto;


import java.time.LocalDate;

public record ProductHistoryDto(String productName, LocalDate date, double price) {}