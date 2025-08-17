package ru.marina.base;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A custom converter for converting a string representation of a date to a Date object.
 */
public class DateConverter extends AbstractBeanField {
    /**
     * Converts a string representation of a date to a Date object.
     *
     * @param s the string representation of the date
     * @return the converted Date object
     * @throws CsvDataTypeMismatchException if the conversion fails due to mismatched data type
     */
    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException {
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}