package me.viharev.debug.services;

import java.io.File;
import java.nio.file.Path;

public interface FileRecipeService {
    boolean saveToFile(String json);

    String readFromFile();

    File getDataFile();

    Path createTempFile(String suffix);

    boolean cleanDataFile();
}
