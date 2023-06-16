package com.example.blog.base.enums;

public enum LikeStatusEnum {
    LIKED((byte) 1),
    NOT_LIKED((byte) 0);
    private byte value;

    LikeStatusEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
