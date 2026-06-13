package com.example.library_system.repository;

import com.example.library_system.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // 一覧の並び替え（必要に応じてソート順を変更）
    List<Item> findAllByOrderByIdDesc();

    // 一覧の並び替え（必要に応じてソート順を変更）
    List<Item> findByStatusOrderByReturnDueDateAsc(String status);

}