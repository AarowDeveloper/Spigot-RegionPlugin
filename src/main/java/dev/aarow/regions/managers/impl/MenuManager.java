package dev.aarow.regions.managers.impl;

import dev.aarow.regions.adapters.menu.Menu;
import dev.aarow.regions.adapters.menu.PaginatedMenu;
import dev.aarow.regions.managers.Manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager extends Manager {

    private Map<UUID, Menu> menuCache = new HashMap<>();
    private Map<UUID, PaginatedMenu> paginatedMenuCache = new HashMap<>();

    @Override
    public void setup() {}

    public Map<UUID, Menu> getMenuCache() {
        return menuCache;
    }

    public Map<UUID, PaginatedMenu> getPaginatedMenuCache() {
        return paginatedMenuCache;
    }
}