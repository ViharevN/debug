package me.viharev.debug.services;

import me.viharev.debug.models.Recipe;

import java.util.Map;

public interface RecipeService {
    void addRecipe(Recipe recipe);


    Recipe getRecipeById(Long id);

    Recipe editRecipe(Long id, Recipe recipe);

    void deleteRecipeById(Long id);

    Map<Long, Recipe> getAllRecipes();

}
