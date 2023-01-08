package me.viharev.debug.services.impl;

import me.viharev.debug.services.FileRecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileRecipeServiceImpl implements FileRecipeService {

    @Value("${path.to.file.recipe.json}")
    private String pathToRecipe;
    @Value("${name.of.file.recipe.json}")
    private String nameOfRecipe;

    @Override
    public boolean saveToFile(String json) {
        cleanDataFile();
        try {
            Files.writeString(Path.of(pathToRecipe, nameOfRecipe), json);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String readFromFile() {
        try {
           return Files.readString(Path.of(pathToRecipe, nameOfRecipe));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public File getDataFile() {
        return new File(pathToRecipe + "/" + nameOfRecipe);
    }

    @Override
    public Path createTempFile(String suffix) {
        try {
           return Files.createTempFile(Path.of(pathToRecipe), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean cleanDataFile() {
        try {
            Files.deleteIfExists(Path.of(pathToRecipe, nameOfRecipe));
            Files.createFile(Path.of(pathToRecipe, nameOfRecipe));
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
