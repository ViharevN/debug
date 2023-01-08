package me.viharev.debug.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.viharev.debug.models.Ingredient;
import me.viharev.debug.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ingredients")
@Tag(
        name = "Контроллер ингредиентов",
        description = "CRUD-операции с ингредиентами")
public class IngredientController {
    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/add/ingredient")
    @Operation(
            summary = "Добавление ингредиента",
            description = "Добавление ингредиента через POST",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент добавлен",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = Ingredient.class
                                    )
                            )
                    )
    )

    )
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok().body(ingredient);
    }

    @GetMapping("/get/by/id")
    @Operation(
            summary = "Поиск ингредиента",
            description = "Поиск и вывод ингредиента по его Id"
    )
    @ApiResponses(
            value = @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    )
    public Ingredient getIngredientById(Long id) {
        return ingredientService.getIngredientById(id);
    }

    @PutMapping("/edit/{id}")
    @Operation(
            summary = "Изменение ингредиента",
            description = "Поиск и изменение ингредиента по его Id"
    )
    @ApiResponses(
            value = @ApiResponse(
                    responseCode = "200",
                    description = "Успешное изменение рецепта",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    )
    public ResponseEntity<Ingredient> editIngredient(@PathVariable Long id, @RequestBody Ingredient ingredient) {
        ingredientService.editIngredientById(id, ingredient);
        return ResponseEntity.ok().body(ingredient);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Удаление ингредиента",
            description = "Поиск и удаление ингредиента по его Id"
    )
    @ApiResponses(
            value = @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент удален",
                    content = @Content(
                            mediaType = "application/json"
                    )
            )
    )
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredientById(id);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Вывод всех ингредиентов",
            description = "Выводим карту всех ингредиентов"
    )
    @ApiResponses(
            value = @ApiResponse(
                    responseCode = "200",
                    description = "Успешный вывод карты всех ингредиентов"
            )
    )
    public Map<Long, Ingredient> getAllIngredients() {
       return ingredientService.getAllIngredients();
    }
}
