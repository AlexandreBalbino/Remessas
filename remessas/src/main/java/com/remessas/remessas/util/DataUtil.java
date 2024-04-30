package com.remessas.remessas.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DataUtil {
    private static final String MM_DD_YYYY = "MM-dd-yyyy";

    public static LocalDateTime obtemDataHojeInicial() {
        LocalDate currentDate = LocalDate.now();
        LocalTime initialTime = LocalTime.of(0, 0);
        return LocalDateTime.of(currentDate, initialTime);
    }

    public static LocalDateTime obtemDataHojeFinal() {
        LocalDate currentDate = LocalDate.now();
        LocalTime initialTime = LocalTime.of(23, 59, 59);
        return LocalDateTime.of(currentDate, initialTime);
    }

    public static String obtemDataHojeFormatada() {
        LocalDate dataAtual = LocalDate.now();
        return formataData(dataAtual);
    }

    public static String formataData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MM_DD_YYYY);
        return data.format(formatter);
    }
}
