package com.remessas.remessas.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DataUtil {
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
}
