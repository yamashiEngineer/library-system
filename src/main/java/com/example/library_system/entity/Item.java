package com.example.library_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "名称は必須入力です")
    @Size(max = 100, message = "名称は100文字以内で入力してください")
    private String title;

    @NotBlank(message = "カテゴリ/ステータスは必須です")
    private String borrowerName;

//    @NotNull(message = "数値は必須入力です")
//    @Min(value = 0, message = "0以上の数値を入力してください")
//    private Integer amount;
//
    @NotNull(message = "貸出日は必須です")
    private LocalDate rentalDate;

    @NotNull(message = "返却期限日は必須です")
    private LocalDate returnDueDate;

    private String status; // '貸出中', '返却済'

    // 日付が必要な場合は有効化
    // private LocalDate targetDate;
}