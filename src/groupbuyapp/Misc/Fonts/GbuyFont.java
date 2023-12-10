package groupbuyapp.Misc.Fonts;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

/**
 * The {@code GbuyFont} class is responsible for creating and registering custom fonts in the Java AWT GraphicsEnvironment.
 * 
 * <p>Font Families include {@code MULI}.
 * 
 * @author yander
 */
public class GbuyFont {
    
    public static final Font MULI_BOLD = createFont("src/groupbuyapp/Misc/Fonts/Muli/Muli-Bold.ttf");
    public static final Font MULI_EXTRA_LIGHT = createFont("src/groupbuyapp/Misc/Fonts/Muli/Muli-ExtraLight.ttf");
    public static final Font MULI_LIGHT = createFont("src/groupbuyapp/Misc/Fonts/Muli/Muli-Light.ttf");
    public static final Font MULI_SEMI_BOLD = createFont("src/groupbuyapp/Misc/Fonts/Muli/Muli-SemiBold.ttf");

    /**
     * Creates a custom font from a TrueType font file and registers it in the Java AWT GraphicsEnvironment.
     *
     * @param path The file path of the TrueType font file.
     * @return The custom font created from the TrueType font file.
     */
    private static Font createFont(String path) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
