package me.viharev.debug.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.viharev.debug.models.Ingredient;
import me.viharev.debug.services.FileIngredientService;
import me.viharev.debug.services.IngredientService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private FileIngredientService fileIngredientService;
    private Map<Long, Ingredient> ingredientMap = new LinkedHashMap<>();
    private static long counter = 0;

    public IngredientServiceImpl(FileIngredientService fileIngredientService) {
        this.fileIngredientService = fileIngredientService;
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
    public void addIngredient(Ingredient ingredient) {
        ingredientMap.put(counter, ingredient);
        saveToFile();
        counter++;
    }
    @Override
    public Ingredient getIngredientById(Long id) {
        for (Long aLong : ingredientMap.keySet()) {
            if (id.equals(aLong)) {
                readFromFile();
                return ingredientMap.get(id);
            }
        }
        return null;
    }
    @Override
    public Ingredient editIngredientById(Long id, Ingredient ingredient) {
        for (Long aLong : ingredientMap.keySet()) {
            if (id.equals(aLong)) {
                ingredientMap.put(id, ingredient);
                saveToFile();
                return ingredient;
            }
        }
        return null;
    }

    @Override
    public void deleteIngredientById(Long id) {
        for (Long aLong : ingredientMap.keySet()) {
            if (id.equals(aLong)) {
                ingredientMap.remove(id);
                saveToFile();
            }
        }
    }
    @Override
    public Map<Long, Ingredient> getAllIngredients() {
        if (!ingredientMap.isEmpty()) {
            readFromFile();
            return ingredientMap;
        }
        return null;
    }
    private void readFromFile() {
        String json = fileIngredientService.readFromFile();
        try {
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Long, Ingredient>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            fileIngredientService.saveToFile(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
