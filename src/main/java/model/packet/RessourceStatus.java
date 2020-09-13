package model.packet;


public enum RessourceStatus {

    AVAILABLE("1"),
    BUSY("2"),
    GONE("3"),
    BACK("4");

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