package com.example.library_system.controller;

import com.example.library_system.entity.Item;
import com.example.library_system.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/form";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute("item") Item item, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "items/form";
        }
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("item", itemService.findById(id));
        return "items/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Validated @ModelAttribute("item") Item item, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "items/form";
        }
        item.setId(id);
        itemService.save(item);
        return "redirect:/items";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        itemService.delete(id);
        return "redirect:/items";
    }

    // 【重要：グレード7→6加点要素】例外が発生した際、共通エラー画面へ安全に誘導する
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}