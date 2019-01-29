package parkeersimulator.enums.theme;

import java.awt.*;

/**
 * Register all fonts used in the theme.
 */
public enum ThemeFonts {

    LARGE_REGULAR (ThemeFonts.defaultFont, Font.PLAIN, 16),
    LARGE_BOLD (ThemeFonts.defaultFont, Font.BOLD, 16),
    NORMAL_REGULAR (ThemeFonts.defaultFont, Font.PLAIN, 14),
    NORMAL_BOLD (ThemeFonts.defaultFont, Font.BOLD, 14),
    SMALL_REGULAR (ThemeFonts.defaultFont, Font.PLAIN, 12);

    private final Font font;
    public static final String defaultFont = "Dubai";

    ThemeFonts(String name, int style, int size){
        font = new Font(name, style, size);
    }

    /**
     * Get the theme font.
     *
     * @return font.
     */
    public Font getFont() {
        return font;
    }
}
