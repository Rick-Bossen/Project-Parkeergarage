package parkeersimulator.enums.theme;

import java.awt.*;

/**
 * Register all theme colors.
 */
public enum ThemeColors {

    INTERACTION (116, 185, 255),

    BACKGROUND_LIGHT (255, 255, 255),
    BACKGROUND_MEDIUM (76, 83, 85),
    BACKGROUND_DARK (45, 52, 54),

    FONT_LIGHT (255, 255, 255),
    FONT_DARK (45, 52, 54),

    CAR_PASSHOLDER (55, 66, 250),
    CAR_PASSHOLDER_SPOT (112, 161, 255),
    CAR_ADHOC (194, 54, 22),
    CAR_RESERVATION (255, 168, 1),
    CAR_RESERVATION_SPOT (52, 231, 228);

    private final Color color;

    ThemeColors(int r, int g, int b){
        color = new Color(r, g, b);
    }

    /**
     * Return the color.
     *
     * @return color.
     */
    public Color getColor() {
        return color;
    }
}
