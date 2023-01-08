package me.viharev.debug.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@Data
@NoArgsConstructor
@NonNull
public class Ingredient {
    private String nameOfIngredient;
    private int amountOfIngredients;
    private String unit;

}
