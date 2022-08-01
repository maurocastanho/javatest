package com.visualnuts;


import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Read a JSON-formatted string and computes some statistics
 * IMPORTANT: the example given is not a valid JSON because the elements 'country' and 'languages' should have been
 * enclosed in quotation marks ("). Because of that and also because the exercise do not specify if the use of
 * a JSON library is acceptable, I coded a simple JSON parser for this exercise. Some lines are commented to show what
 * should be changed to read a proper JSON.
 */
public class Exercise2 {

    /**
     * Stores and computes the final results.
     */
    public class Results {
        int maxCountryLangCount = 0; // maximum languages per country
        List<String> maxCountryLang = new ArrayList<>(); // country(ies) with the most languages
        int maxCountryLanguageCountGe = 0; // maximum languages per German-speaking countries
        List<String> maxCountryLanguagesGe = new ArrayList<>(); // German-speaking country(ies) with the most languages
        Set<String> countrySet = new HashSet<>(); // all countries
        Map<String, Integer> languages = new HashMap<>(); // all languages with number of countries
        Map<String, Set<String>> allCountriesLanguages = new HashMap<>(); // all countries and languages

        /**
         * Count languages by country.
         * @param lang language to update counter
         */
        private void updateLanguageCounter(String lang) {
            Integer countLang = languages.get(lang);
            // Sum up countries by language
            languages.put(lang, countLang == null ? 1 : countLang + 1);
        }

        /**
         * Computes country(ies) with the most spoken languages. More than one if same language count.
         * @param country country name
         * @param languages languages spoken in that country
         */
        private void updateMaxLanguages(String country, Set<String> languages) {
            int numLanguages = languages.size();
            if (maxCountryLangCount < numLanguages) {
                maxCountryLang.clear();
                maxCountryLang.add(country);
                maxCountryLangCount = numLanguages;
            } else if (maxCountryLangCount == numLanguages) {
                maxCountryLang.add(country);
            }
        }

        /**
         * Computes country(ies) with the most spoken languages that includes German.
         * More than one if same language count.
         *
         * @param country country name
         * @param languages languages spoken in that country
         */
        private void updateMaxLanguagesGerman(String country, Set<String> languages) {
            if (!languages.contains("de")) {
                return;
            }
            int numLanguages = languages.size();
            if (maxCountryLanguageCountGe < numLanguages) {
                maxCountryLanguageCountGe = numLanguages;
                maxCountryLanguagesGe.clear();
                maxCountryLanguagesGe.add(country);
            } else if (maxCountryLanguageCountGe == numLanguages) {
                maxCountryLanguagesGe.add(country);
            }
        }

        /**
         * Add a language to the list of languages of a given country
         *
         * @param country country name
         * @param lang language name
         */
        private void addLanguageToCountry(String country, String lang) {
            if (!allCountriesLanguages.containsKey(country)) {
                allCountriesLanguages.put(country, new HashSet<>());
            }
            Set<String> languages = allCountriesLanguages.get(country);
            languages.add(lang);
            allCountriesLanguages.put(country, languages);
        }

        /**
         * Returns spoken languages of a given country
         * @param country country name
         * @return language list
         */
        public Set<String> getLanguagesByCountry(String country) {
            return allCountriesLanguages.get(country);
        }

        /**
         * Returns the most common languages in the world
         * @return stream with country names e language counts
         */
        public Stream<Map.Entry<String, Integer>> mostCommonLanguages() {
            return languages.entrySet()
                    .stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed());
        }

        /**
         * Print the results to stdout
         */
        private void printResults() {
            // Print results
            System.out.println("Number of countries in the world: " + countrySet.size());
            System.out.println("\nCountry(ies) with the most languages (" + maxCountryLanguageCountGe + "), where they officially speak German: ");
            maxCountryLanguagesGe.forEach(System.out::println);
            System.out.println("\nNumber of all official languages: " + languages.size());
            System.out.println("\nCountry(ies) with the highest number of official languages: "
                    + maxCountryLang + ", languages = " + maxCountryLangCount);
            System.out.println("\nMost common official languages: ");
            mostCommonLanguages().forEach((it) -> System.out.println(it.getKey() + ": " + it.getValue()));
        }

    }

    PushbackReader reader;
    int line;
    char[] charBuffer;

    boolean isValidJson; // true if JSON input is valid, false if it is as described in the comment on top of the file


    Exercise2(StringReader sr) {
        init(sr);
        this.isValidJson = false;
    }

    Exercise2(boolean isValidJson, StringReader sr) {
        init(sr);
        this.isValidJson = isValidJson;
    }

    /**
     * Init variables
     */
    private void init(StringReader sReader) {
        charBuffer = new char[1];
        reader = new PushbackReader(sReader);
    }

    /**
     * Tests if ch is an alphabetic character
     *
     * @param ch char input
     * @return true if ch is alphabetic
     */
    boolean isAlpha(char ch) {
        return Character.isAlphabetic(ch);
    }

    int[] whitespace = new int[]{' ', '\n', '\t'};

    /**
     * Tests if ch is a whitespace character
     *
     * @param ch char input
     * @return true if ch is whitespace
     */

    private boolean isWhitespace(char ch) {
        return contains(whitespace, ch);
    }

    /**
     * Read until a non-whitespace character is found
     *
     * @throws IOException invalid input
     */
    void dropWhiteSpace() throws IOException {
        char ch;
        do {
            ch = readChar();
        } while (isWhitespace(ch));
        reader.unread(ch);
    }

    /**
     * Tests if an array contains a key
     *
     * @param arr input array
     * @param key key to be searched
     * @return true if found
     */
    public static boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    /**
     * Reads a char
     *
     * @return char read
     * @throws IOException invalid input
     */
    char readChar() throws IOException {
        if (reader.read(charBuffer) <= 0) {
            throw new IOException("Unexpected end of file in line " + line);
        }
        if (charBuffer[0] == '\n') {
            line++;
        }
        return charBuffer[0];
    }

    /**
     * Reads an alphabetic word
     *
     * @return the word that was read
     * @throws IOException invalid input
     */
    String readWord() throws IOException {
        dropWhiteSpace();
        StringBuilder sb = new StringBuilder();
        char ch = readChar();
        while (isAlpha(ch)) {
            sb.append(ch);
            ch = readChar();
        }
        reader.unread(ch);
        return sb.toString();
    }


    /**
     * Asserts that a definite char is the next character of the input
     *
     * @param expected char expected
     * @throws IOException invalid input
     */
    void expectChar(char expected) throws IOException {
        dropWhiteSpace();
        char ch = readChar();
        if (expected != ch) {
            throw new IOException("Unexpected char: [" + ch + "], expected: [" + expected + "] in line " + line);
        }
    }

    /**
     * Asserts that a definite word is the next element of the input
     *
     * @param expected char expected
     * @throws IOException invalid input
     */
    void expectWord(String expected) throws IOException {
        String lastRead = readWord();
        if (!expected.equals(lastRead)) {
            throw new IOException("Unexpected word: [" + lastRead + "], expected: [" + expected + "] in line " + line);
        }
    }

    /**
     * Tests if a definite string is the next element of the input
     *
     * @param expected string expected
     * @return true if read == expected
     * @throws IOException invalid input
     */
    boolean optionalChar(char expected) throws IOException {
        dropWhiteSpace();
        char ch = readChar();
        if (expected != ch) {
            reader.unread(ch);
            return false;
        }
        return true;
    }

    /**
     * Reads and processes the JSON
     *
     * @throws IOException invalid input
     */
    Results processJson() throws IOException {
        line = 1;
        Results results = new Results();
        expectChar('[');

        // Read countries
        do {
            expectChar('{');
            if (isValidJson) {
                expectChar('"'); // see comment in the start of file
            }
            expectWord("country");
            if (isValidJson) {
                expectChar('"'); // see comment in the start of file
            }
            expectChar(':');
            expectChar('"');
            String country = readWord();
            expectChar('"');

            // Add to set of countries
            results.countrySet.add(country);
            expectChar(',');

            if (isValidJson) {
                expectChar('"'); // see comment in the start of file
            }
            expectWord("languages");
            if (isValidJson) {
                expectChar('"'); // see comment in the start of file
            }
            expectChar(':');
            expectChar('[');

            // Read languages
            do {
                expectChar('"');
                String lang = readWord();
                expectChar('"');
                results.updateLanguageCounter(lang);

                // Store language in country
                results.addLanguageToCountry(country, lang);

            } while (optionalChar(','));

            Set<String> languages = results.getLanguagesByCountry(country);
            // Computes countries with the most languages that includes german
            results.updateMaxLanguagesGerman(country, languages);

            // Computes countries with more languages
            results.updateMaxLanguages(country, languages);
            expectChar(']');
            expectChar('}');
        } while (optionalChar(','));

        return results;
    }


    /**
     * Main program. More tests in the test class
     * @param args ignored
     */
    public static void main(String[] args) {
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
        try {
            Results results = ex2.processJson();
            results.printResults();
        } catch (IOException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
