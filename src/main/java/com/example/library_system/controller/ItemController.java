package com.example.library_system.controller;

import com.example.library_system.entity.Item;
import com.example.library_system.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.example.library_system.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    // このコントローラー内の全メソッドが呼ばれる前に、自動でModelに"statusMap"が追加されます
    @ModelAttribute("statusMap")
    public Map<String, String> setupStatusMap() {
        Map<String, String> statusMap = new LinkedHashMap<>();
        statusMap.put("貸出中", "貸出中");
        statusMap.put("返却済", "返却済");
        return statusMap;
    }

    @GetMapping
    public String list(Model model) {
        // 「貸出中」のレコードのみを、期限日の昇順で取得
        List<Item> rentals = itemService.findActiveRentals();
        model.addAttribute("items", rentals);

        // id の降順（値が大きい順・新しい順）で、すべてのデータを取得する場合
//        model.addAttribute("items", itemService.findAll());
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

    // IllegalArgumentException（ID未存在）と MethodArgumentTypeMismatchException（IDの型不正）を両方キャッチ
    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    public String handleException(Exception ex, RedirectAttributes redirectAttributes) {
        // リダイレクト先（一覧画面）に1度だけ引き継がれるメッセージを設定
        redirectAttributes.addFlashAttribute("errorMessage", "指定されたデータが見つからないか、URLが不正です。");

        // 一覧画面のURLへリダイレクト
        return "redirect:/items";
    }
}