package me.viharev.debug.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import me.viharev.debug.models.Recipe;
import me.viharev.debug.services.FileRecipeService;
import me.viharev.debug.services.RecipeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@NonNull
public class RecipeServiceImpl implements RecipeService {
    private FileRecipeService fileRecipeService;
    private Map<Long, Recipe> recipeMap = new LinkedHashMap<>();
    private static long counter = 0;

    public RecipeServiceImpl(FileRecipeService fileRecipeService) {
        this.fileRecipeService = fileRecipeService;
    }
    @PostConstruct
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeMap.put(counter, recipe);
        saveToFile();
        counter++;
    }
    @Override
    public Recipe getRecipeById(Long id) {
        for (Long aLong : recipeMap.keySet()) {
            if (id.equals(aLong)) {
                readFromFile();
               return recipeMap.get(id);
            }
        }
        return null;
    }
    @Override
    public Recipe editRecipe(Long id, Recipe recipe) {
        for (Long aLong : recipeMap.keySet()) {
            if (id.equals(aLong)) {
                recipeMap.put(id, recipe);
                saveToFile();
                return recipe;
            }
        }
        return null;
    }
    @Override
    public void deleteRecipeById(Long id) {
        for (Long aLong : recipeMap.keySet()) {
            if (id.equals(aLong)) {
                recipeMap.remove(id);
                saveToFile();
            }
        }
    }
    @Override
    public Map<Long, Recipe> getAllRecipes() {
        if (!recipeMap.isEmpty()) {
            readFromFile();
            return recipeMap;
        }
        return null;
    }

    private void readFromFile() {
        String json = fileRecipeService.readFromFile();
        try {
           recipeMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Recipe>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileRecipeService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
