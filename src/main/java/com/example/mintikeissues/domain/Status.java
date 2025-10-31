package com.example.mintikeissues.domain;

public enum Status {
    NEW("新規提案"),
    PENDING("検討待ち"),
    IN_REVIEW("検討中"),
    ESCALATED("社長/役員判断待ち"),
    APPROVED("実行決定"),
    REJECTED("実行しない/見送り"),
    IN_PROGRESS("実行中"),
    HOLD("保留/中断"),
    VERIFICATION("完了報告待ち"),
    CLOSED("完了");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Status fromDisplay(String display) {
        for (Status s : values()) {
            if (s.displayName.equals(display) || s.name().equalsIgnoreCase(display)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + display);
    }
}


