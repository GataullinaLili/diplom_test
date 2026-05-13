package com.itemstorage.enums;

public enum Role {
    ADMIN("Администратор"),
    STOREKEEPER("Сотрудник склада"),
    RECEPTIONIST("Приёмщик");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
