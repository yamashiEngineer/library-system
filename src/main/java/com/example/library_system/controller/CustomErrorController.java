package com.example.library_system.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // Spring Bootが検知したエラーのステータスコード（404や500など）を取得
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // 404 Not Found（ページが見つからない）の場合
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // リダイレクト先へメッセージを一度だけ引き継ぐ（フラッシュスコープ）
                redirectAttributes.addFlashAttribute("errorMessage", "アクセスしようとしたページ（URL）は存在しないか、アドレスが間違っています。");
                return "redirect:/items"; // 一覧画面へリダイレクト
            }
        }

        // 404以外（500サーバーエラーなど）が起きた場合は、安全のために用意されている「error.html」を表示させる
        return "error";
    }
}