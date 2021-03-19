package com.acazia.music.base;

import lombok.NonNull;

public class PermissionData<E extends BaseEntity> {
    private boolean hasPermission;

    @NonNull
    private E entity;
}
