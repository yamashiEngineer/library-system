package com.example.library_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotNull;

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

    @NotBlank(message = "貸出先名は必須です")
    private String borrowerName;

    @NotNull(message = "貸出日は必須です")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate rentalDate;

    @NotNull(message = "返却期限日は必須です")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate returnDueDate;

    private String status; // '貸出中', '返却済'
}