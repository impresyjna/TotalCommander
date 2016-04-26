package jcommander.utils;

import java.util.Locale;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by impresyjna on 26.04.2016.
 */
public class AppBundle extends Observable {
    private static AppBundle ourInstance = new AppBundle();
    private Locale currentLocale;
    private Locale englishLocale;
    private Locale polishLocale;
    private ResourceBundle bundle;

    public static AppBundle getInstance() {
        return ourInstance;
    }

    private AppBundle() {
        this.englishLocale = new Locale("en");
        this.polishLocale = new Locale("pl");
        this.currentLocale = polishLocale;
        this.bundle = ResourceBundle.getBundle("lang", currentLocale);
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String locale) {
        if(locale.equals("en")) {
            this.currentLocale = englishLocale;
        } else {
            this.currentLocale = polishLocale;
        }
        updateBundle();
    }

    synchronized private void updateBundle() {
        this.bundle = ResourceBundle.getBundle("lang", currentLocale);
        this.setChanged();
        this.notifyObservers();
    }
}
