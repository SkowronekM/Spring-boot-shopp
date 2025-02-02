package pl.storeez.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.storeez.domain.clothes.ClothesService;
import pl.storeez.domain.clothes.dto.ClothesDto;

import java.math.BigDecimal;

@Controller
public class ClothesController {
    private final ClothesService clothesService;

    public ClothesController(ClothesService clothesService) {
        this.clothesService = clothesService;
    }

    @GetMapping("/clothes/{subcategory}/{id}")
    public String getClothes(@PathVariable String subcategory, @PathVariable long id, Model model) {
        ClothesDto clothes = clothesService.findClothesById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BigDecimal discountedPrice = clothesService.getDiscountedPrice(clothes);
        clothes.setPriceAfterDiscount(discountedPrice);

        model.addAttribute("clothes", clothes);
        model.addAttribute("heading", "Product details");

        return "clothes";
    }
}
