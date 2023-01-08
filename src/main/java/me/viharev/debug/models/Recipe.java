package me.viharev.debug.models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@NonNull
@Data
public class Recipe {
    private String nameRecipe;
    private int timeOfPreparing;
    private final List<Ingredient> ingredients = new ArrayList<>();
    private final List<String> steps = new ArrayList<>();
}
