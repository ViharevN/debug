package me.viharev.debug.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.viharev.debug.services.FileRecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/files/recipes")
@Tag(
        name = "Контроллер рецептов",
        description = "Импорт/экспорт файлов рецептов"
)
public class FileRecipeController {
    private FileRecipeService fileRecipeService;

    public FileRecipeController(FileRecipeService fileRecipeService) {
        this.fileRecipeService = fileRecipeService;
    }

    @GetMapping(value = "/files/export")
    @Operation(
            summary = "Экспорт файлов рецептов",
            description = "Здесь вы можете экспортировать в json-файл рецепты"

    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "файл успешно скачался"),
            @ApiResponse(responseCode = "400", description = "плохой запрос, отправлен некорректный запрос серверу"),
            @ApiResponse(responseCode = "500", description = "сервер столкнулся с неожиданной ошибкой, которая помешала ему выполнить запрос")})
    public ResponseEntity<InputStreamResource> downloadFile() {
        File file = fileRecipeService.getDataFile();
        InputStreamResource resource = null;
        try {
            if (file.exists()) {
                resource = new InputStreamResource(new FileInputStream(file));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(file.length())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"recipes.json\"")
                .body(resource);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Импорт рецептов из файла"
    )
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "файл успешно загружен"),
            @ApiResponse(responseCode = "400", description = "плохой запрос, отправлен некорректный запрос серверу"),
            @ApiResponse(responseCode = "500", description = "сервер столкнулся с неожиданной ошибкой, которая помешала ему выполнить запрос")})
    public ResponseEntity<Void> uploadDataFile(@RequestParam MultipartFile file) {
        fileRecipeService.cleanDataFile();
        File dataFile = fileRecipeService.getDataFile();
        try (
                FileOutputStream fos = new FileOutputStream(dataFile)
        ) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
