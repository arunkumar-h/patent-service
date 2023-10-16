package com.technovigil.patent.util;

import com.opencsv.CSVWriter;
import com.technovigil.patent.model.Patent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {
    public static byte[] convertPatentsToCsv(List<Patent> patents) throws IOException {
        // Write to CSV
        StringWriter stringWriter = new StringWriter();
        try (CSVWriter csvWriter = new CSVWriter(stringWriter)) {
            // Writing header
            String[] headers = generateHeaders(Patent.class);
            csvWriter.writeNext(headers);

            // Writing data
            for (Patent patent : patents) {
                String[] data = generateData(patent);
                csvWriter.writeNext(data);
            }
        }
       return stringWriter.toString().getBytes();
    }

    private static String[] generateHeaders(Class<?> clazz) {
        List<String> headers = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            headers.add(field.getName());
        }
        return headers.toArray(new String[0]);
    }

    private static String[] generateData(Patent patent) {
        List<String> data = new ArrayList<>();
        for (Field field : patent.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(patent);
                data.add(value != null ? value.toString() : "");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                data.add(""); // Handle the exception as per your needs
            }
        }
        return data.toArray(new String[0]);
    }


}
