package me.viharev.debug.services.impl;

import me.viharev.debug.services.FileIngredientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileIngredientServiceImpl implements FileIngredientService {
    @Value("${path.to.file.ingredient.json}")
    private String pathToIngredient;
    @Value("${name.of.file.ingredient.json}")
    private String nameOfIngredient;

    @Override
    public boolean saveToFile(String json) {
        cleanToFile();
        try {
            Files.writeString(Path.of(pathToIngredient, nameOfIngredient), json);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String readFromFile() {
        try {
           return Files.readString(Path.of(pathToIngredient, nameOfIngredient));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File getDataFile() {
        return new File(pathToIngredient + "/" + nameOfIngredient);
    }
    @Override
    public void cleanToFile() {
        try {
            Files.deleteIfExists(Path.of(pathToIngredient, nameOfIngredient));
            Files.createFile(Path.of(pathToIngredient, nameOfIngredient));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
