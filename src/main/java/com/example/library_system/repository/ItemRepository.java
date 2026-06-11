package com.example.library_system.repository;

import com.example.library_system.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 一覧の並び替え（必要に応じてソート順を変更）
    List<Item> findAllByOrderByIdDesc();

}