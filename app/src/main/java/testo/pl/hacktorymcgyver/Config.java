package testo.pl.hacktorymcgyver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukasz Marczak on 2015-07-04.
 */
public class Config {
    public static final String SERVER_URL = "ggg";
    public static final String GREEN_BEACON_NAME = "GREEN BEACON";
    public static final String PINK_BEACON_NAME = "PINK BEACON";
    public static List<Record> highScores = new ArrayList<>();

    public static List<Record> getHighScores() {
        return highScores;
    }
}
