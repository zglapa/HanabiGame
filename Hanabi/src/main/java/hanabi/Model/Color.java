package hanabi.Model;

public enum Color {
    WHITE, YELLOW, RED, GREEN, BLUE, RAINBOW;


    public static Color getReverseOrdinal(int val) {
        for (Color c : Color.values()) {
            if (c.ordinal() == val)
                return c;
        }
        return null;
    }
}
