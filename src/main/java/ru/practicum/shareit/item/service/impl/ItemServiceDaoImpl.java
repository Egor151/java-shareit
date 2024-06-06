package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceDao;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemServiceDaoImpl implements ItemServiceDao {

    private  final Map<Long, List<Item>> items = new HashMap<>();

    private Long generateId = 1L;

    @Override
    public Item add(Item item) {
        item.setId(generateId);
        generateId++;
        List<Item> listItems = new ArrayList<>();
        listItems.add(item);
        items.put(item.getOwner(), listItems);
        return item;
    }

    @Override
    public Item update(Item item) {
        List<Item> userItems = items.get(item.getOwner());
        List<Item> removeItems = userItems.stream()
                .filter(userItem -> userItem.getId().equals(item.getId()))
                .collect(Collectors.toList());
        userItems.remove(removeItems);
        userItems.add(item);
        return item;
    }

    @Override
    public Optional<Item> findItemById(Long itemId) {
        return items.values().stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
    }

    @Override
    public List<Item> findAll(Long userId) {
        return new ArrayList<>(items.get(userId));
    }

    @Override
    public List<Item> search(String text) {
        String searchText = text.toLowerCase();
        return items.values().stream()
                .flatMap(Collection::stream)
                .filter(Item::getAvailable)
                .filter(item -> item.getName().toLowerCase().contains(searchText)
                        || item.getDescription().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
    }
}
