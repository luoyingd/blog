package com.example.blog.base.enums;

public enum MineCommentStatusEnum {
    MINE((byte) 1),
    NOT_MINE((byte) 0);
    private byte value;

    MineCommentStatusEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
