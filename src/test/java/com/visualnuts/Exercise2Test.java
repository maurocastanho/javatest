package com.visualnuts;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Exercise2Test {

    @Test
    void testEmptyString() throws IOException {
        Exercise2 ex2 = new Exercise2(new StringReader(
                ""
        ));
        assertThrows(IOException.class, ex2::processJson);
    }

        @Test
    void testEmptyArray() throws IOException {
        Exercise2 ex2 = new Exercise2(new StringReader(
                "[]"
        ));
        assertThrows(IOException.class, ex2::processJson);
    }


    @Test
    void testOneCountry() throws IOException {
        Exercise2 ex2 = new Exercise2(new StringReader(
                "[{\n" +
                        "country: \"US\",\n" +
                        "languages: [ \"en\" ]\n" +
                        "}" +
                        "]"
        ));
        Exercise2.Results results = ex2.processJson();
        assertEquals(results.countrySet.size(), 1);
        assertEquals(results.languages.size(), 1);
        assertEquals(results.maxCountryLang.get(0), "US");
        assertEquals(results.maxCountryLangCount, 1);
        assertEquals(results.maxCountryLanguagesGe.size(), 0);
        assertEquals(results.maxCountryLanguageCountGe, 0);
        List<Map.Entry<String, Integer>> mostCommon = results.mostCommonLanguages().collect(Collectors.toList());
        assertEquals(mostCommon.get(0).getKey(), "en");
        assertEquals(mostCommon.get(0).getValue(), 1);
    }


    @Test
    void testOriginalJson() throws IOException {
        Exercise2 ex2 = new Exercise2(new StringReader(
                "[\n" +
                        "{\n" +
                        "country: \"US\",\n" +
                        "languages: [ \"en\" ]\n" +
                        "},\n" +
                        "{\n" +
                        "country: \"BE\",\n" +
                        "languages: [ \"nl\", \"fr\", \"de\" ]\n" +
                        "},\n" +
                        "{\n" +
                        "country: \"NL\",\n" +
                        "languages: [ \"nl\", \"fy\" ]\n" +
                        "},\n" +
                        "{\n" +
                        "country: \"DE\",\n" +
                        "languages: [ \"de\" ]\n" +
                        "},\n" +
                        "{\n" +
                        "country: \"ES\",\n" +
                        "languages: [ \"es\" ]\n" +
                        "}\n" +
                        "]"));
        Exercise2.Results results = ex2.processJson();
        assertEquals(results.countrySet.size(), 5);
        assertEquals(results.languages.size(), 6);
        assertEquals(results.maxCountryLang.get(0), "BE");
        assertEquals(results.maxCountryLangCount, 3);
        assertEquals(results.maxCountryLanguagesGe.get(0), "BE");
        assertEquals(results.maxCountryLanguageCountGe, 3);
        List<Map.Entry<String, Integer>> mostCommon = results.mostCommonLanguages().collect(Collectors.toList());
        assertEquals(mostCommon.get(0).getKey(), "de");
        assertEquals(mostCommon.get(0).getValue(), 2);
        assertEquals(mostCommon.get(1).getKey(), "nl");
        assertEquals(mostCommon.get(1).getValue(), 2);
    }

    @Test
    void testValidJson() throws IOException {
        Exercise2 ex2 = new Exercise2(true, new StringReader(
                "[\n" +
                        "{\n" +
                        "\"country\": \"US\",\n" +
                        "\"languages\": [ \"en\" ]\n" +
                        "},\n" +
                        "{\n" +
                        "\"country\": \"BE\",\n" +
                        "\"languages\": [ \"nl\", \"fr\", \"de\" ]\n" +
                        "},\n" +
                        "{\n" +
                        "\"country\": \"NL\",\n" +
                        "\"languages\": [ \"nl\", \"fy\" ]\n" +
                        "},\n" +
                        "{\n" +
                        "\"country\": \"DE\",\n" +
                        "\"languages\": [ \"de\" ]\n" +
                        "},\n" +
                        "{\n" +
                        "\"country\": \"ES\",\n" +
                        "\"languages\": [ \"es\" ]\n" +
                        "}\n" +
                        "]"));
        Exercise2.Results results = ex2.processJson();
        assertEquals(results.countrySet.size(), 5);
        assertEquals(results.languages.size(), 6);
        assertEquals(results.maxCountryLang.get(0), "BE");
        assertEquals(results.maxCountryLangCount, 3);
        assertEquals(results.maxCountryLanguagesGe.get(0), "BE");
        assertEquals(results.maxCountryLanguageCountGe, 3);
        List<Map.Entry<String, Integer>> mostCommon = results.mostCommonLanguages().collect(Collectors.toList());
        assertEquals(mostCommon.get(0).getKey(), "de");
        assertEquals(mostCommon.get(0).getValue(), 2);
        assertEquals(mostCommon.get(1).getKey(), "nl");
        assertEquals(mostCommon.get(1).getValue(), 2);
    }

}