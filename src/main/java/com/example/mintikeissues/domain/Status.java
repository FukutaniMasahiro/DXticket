package com.example.mintikeissues.domain;

public enum Status {
    受付,
    検討待ち,
    検討中,
    検討済み,
    承認依頼中,
    承認済み,
    否認,
    実行中,
    完了,
    対応無し;

    public static Status fromDisplay(String value) {
        for (Status s : values()) {
            if (s.name().equals(value)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + value);
    }
}


