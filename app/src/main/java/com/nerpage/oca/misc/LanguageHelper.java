package com.nerpage.oca.misc;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageHelper {

    public static void changeLocale(Resources res, String locale) {

        Configuration config;
        config = new Configuration(res.getConfiguration());


        switch (locale) {
            case "pl":
                config.locale = new Locale("pl");
                break;
            default:
                config.locale = Locale.ENGLISH;
                break;
        }
        res.updateConfiguration(config, res.getDisplayMetrics());
        // reload files from assets directory
    }
}
