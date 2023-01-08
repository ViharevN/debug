package me.viharev.debug.services;

import java.io.File;

public interface FileIngredientService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    void cleanToFile();
}
