package com.ngu.pizzamania.Controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ApiResponse;
import com.ngu.pizzamania.Model.ErrorResponse;
import com.ngu.pizzamania.Model.User;
import com.ngu.pizzamania.Service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ngu.pizzamania.Model.Pizza;
import com.ngu.pizzamania.Service.PizzaService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/pizza")
@AllArgsConstructor
public class PizzaController {
    private final PizzaService pizzaService;
    private ObjectMapper objectMapper;

    private FileStorageService fileStorageService;

    /**
     * @param pizza
     * @return
     */
    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createPizza(@RequestPart("pizza") Pizza pizza, @RequestPart(name = "pizzaImage")MultipartFile pizzaImage,@RequestPart(name = "pizzaBannerImage") MultipartFile pizzaBannerImage) throws IOException {

        final List<String> downloadUri = extractPizzaImagesString(pizzaImage, pizzaBannerImage);

        pizza.setPizzaImage(downloadUri.get(0));
        pizza.setPizzaBannerImage(downloadUri.get(1));
        Pizza savedPizza = pizzaService.createPizza(pizza);
        return ResponseEntity.ok()
                .body(ApiResponse.builder()
                        .message("pizza saved successfully")
                        .data(savedPizza)
                        .statusCode(HttpStatus.CREATED.value())
                        .httpStatus(HttpStatus.CREATED)
                        .timeStamp(new Date())
                        .build()
                );
    }

    private List<String> extractPizzaImagesString(MultipartFile pizzaImage, MultipartFile pizzaBannerImage) throws IOException {
        List<MultipartFile> multipartFiles = Arrays.asList(pizzaImage, pizzaBannerImage);
        final List<String> imageStrings = fileStorageService.storeMultipleFiles(multipartFiles);
        return imageStrings.stream()
                .map(imageString ->
                        ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                                .path(imageString).toUriString()
                ).toList();
    }
    /**
     * @param pizza
     * @return
     */
    @PutMapping(value = "/updates/{id}")
    public ResponseEntity<Object> updatePizza(@RequestPart("pizza") Pizza pizza, @RequestPart(name = "pizzaImage")MultipartFile pizzaImage,@RequestPart(name = "pizzaBannerImage") MultipartFile pizzaBannerImage) throws IOException {
        if(pizza != null){
            final List<String> downloadUri = extractPizzaImagesString(pizzaImage, pizzaBannerImage);

            pizza.setPizzaImage(downloadUri.get(0));
            pizza.setPizzaBannerImage(downloadUri.get(1));
            return ResponseEntity.ok(pizzaService.updatePizza(pizza));
        }
        return ResponseEntity.badRequest()
                .body("Pizza not found");
    }
    @PutMapping(value = "/updateImages/{id}")
    public ResponseEntity<Object> updatePizzaImages(@PathVariable Integer id,@RequestParam(required = false,value = "pizzaImage") MultipartFile pizzaImage,@RequestParam(required = false,value = "pizzaBannerImage")MultipartFile pizzaBannerImage) throws IOException {
         Pizza pizza = pizzaService.findPizzaById(id);
        if(pizza != null){
            final List<String> downloadUri = extractPizzaImagesString(pizzaImage, pizzaBannerImage);

            pizza.setPizzaImage(downloadUri.get(0));
            pizza.setPizzaBannerImage(downloadUri.get(1));
            return ResponseEntity.ok(pizzaService.updatePizza(pizza));
        }
        return ResponseEntity.badRequest()
                .body("Pizza with id "+id+" not found");
    }

    /**
     * @return all pizza
     */
    @GetMapping("/pizzas")
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    @GetMapping("/{id}")
    public Pizza findPizzaById(@PathVariable int id) {
        return pizzaService.findPizzaById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePizzaById(@PathVariable int id) {

        pizzaService.deletePizzaById(id);

        return "Pizza deleted with Id " + id;
    }

    /**
     * @return all pizza by their category
     */
    @GetMapping("/groupByCategory")
    public Map<String, List<Pizza>> getPizzaByCategory() {
        List<Pizza> allpizzas = pizzaService.getAllPizzas();
        Map<String, List<Pizza>> groupByCategory = allpizzas.stream()
                .collect(Collectors.groupingBy(pizza -> pizza.getCategory().getName()));
        return groupByCategory;
    }

    @PatchMapping(value = "/update/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Pizza> updatePizza(@PathVariable Integer id, HttpServletRequest request) throws IOException {
        Pizza currentPizza = pizzaService.getPizzaById(id).orElseThrow(()-> new ResourceNotFoundException(
                ErrorResponse.builder()
                        .message("Pizza with id "+id+" does not exists")
                        .build()
        ));
        Pizza updatedPizza = objectMapper.readerForUpdating(currentPizza).readValue(request.getReader());
        pizzaService.updatePizza(updatedPizza);
        return new ResponseEntity<>(updatedPizza, HttpStatus.ACCEPTED);
    }
}
