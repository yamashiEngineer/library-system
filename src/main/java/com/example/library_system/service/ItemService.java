package com.example.library_system.service;

import com.example.library_system.entity.Item;
import com.example.library_system.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // id の降順（値が大きい順・新しい順）で、すべてのデータを取得する場合
//    public List<Item> findAll() {
//        return itemRepository.findAllByOrderByIdDesc();
//    }

    // 「貸出中」のデータを期限が古い順に取得するサービスメソッド
    public List<Item> findActiveRentals() {
        return itemRepository.findByStatusOrderByReturnDueDateAsc("貸出中");
    }

    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("指定されたデータ（ID: " + id + "）が見つかりません。"));
    }

    @Transactional
    public void save(Item item) {
        // ※ここにドメイン固有の相関チェック（例: 開始日と終了日の逆転など）を挟む
        itemRepository.save(item);
    }

    @Transactional
    public void delete(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new IllegalArgumentException("削除対象のデータ（ID: " + id + "）が存在しません。");
        }
        itemRepository.deleteById(id);
    }
}