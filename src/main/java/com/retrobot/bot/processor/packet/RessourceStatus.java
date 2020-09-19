package com.retrobot.bot.processor.packet;


public enum RessourceStatus {

    AVAILABLE("1"),
    BUSY("2"),
    GONE("3"),
    NOT_AVAILABLE("4"),
    BACK("5");

    public final String status;

    RessourceStatus(String status) {
        this.status = status;
    }

    public static RessourceStatus labelOfStatus(String status) {
        for (RessourceStatus e : values()) {
            if (e.status.equals(status)) {
                return e;
            }
        }
        return null;
    }

}