package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/*
 * class loads in a font from the file system
 */
public class FontLoader {

	// method that loads the font from directory and takes in a font variable
	public static Font loadFont(String path, float size) { // the deriveFont must be a float not double

		// try and catch to see if font exist, if not then print stack trace
		try {

			return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);

		} catch (FontFormatException | IOException e) {

			e.printStackTrace();
			System.exit(1);

		}
		return null;
	}

}
