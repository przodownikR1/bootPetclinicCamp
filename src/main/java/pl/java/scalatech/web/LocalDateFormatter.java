package pl.java.scalatech.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LocalDateFormatter implements Formatter<LocalDate> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        log.info("LocalDateFormatter : parse : {}",s);
        LocalDate date = LocalDate.from(formatter.parse(s));
        return date;
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {
        log.info("print : localDate : {}",localDate);
        return formatter.format(localDate);
    }

 
  
}