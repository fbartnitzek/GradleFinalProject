package com.fbartnitzek.gradle;

import java.util.Random;

public class Joker {

    private static String[] jokes = new String[]{
            //src; http://www.laughfactory.com/jokes/technology-jokes
                "Do not be racist; be like Mario. He's an Italian plumber, who was made by the " +
                 "Japanese, speaks English, looks like a Mexican, jumps like a black man, " +
                 "and grabs coins like a Jew!",
                "Q: Is Google male or female?\n" +
                "A: Female, because it doesn't let you finish a sentence before making a suggestion. ",
            "TODO: more jokes... "
    };
    // quite good idea - using: https://github.com/werkn/icndb-java

    public static String getRandomJoke(){

        int i = new Random().nextInt(jokes.length);
        return jokes[i];
    }

}
