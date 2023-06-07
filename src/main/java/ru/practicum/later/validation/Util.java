package ru.practicum.later.validation;

import lombok.experimental.UtilityClass;
import ru.practicum.later.exception.exp.BadRequestException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class Util {
    final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Instant dateStringToLocalDate(String text) {
        try {
            return LocalDate
                    .parse(text, formatterDate)
                    .atStartOfDay()
                    .toInstant(ZoneOffset.UTC);
        } catch (DateTimeParseException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public void validateDates(Instant startDate, Instant endDate) throws BadRequestException {
        final Instant current = Instant.now();
        if (startDate.isAfter(current)) {
            throw new BadRequestException("Параметр fromDate должен содержать прошедшую дату");
        }
        if (endDate.isAfter(current) || endDate.isBefore(startDate)) {
            throw new BadRequestException("Параметр toDate должен содержать прошедшую дату или не должен быть раньше fromDate");
        }
    }

}
