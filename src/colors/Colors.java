package colors;

import java.awt.*;

public class Colors {

    public static Color lightBlue () {
        return decodeColor("#9BA4C5");
    }

    public static Color darkBlueColor () {
        return decodeColor("#272D43");
    }

    public static Color redColor () {
        return decodeColor("#E54C6B");
    }

    public static Color blueWhiteColor () {
        return decodeColor("#F3FBFC");
    }

    public static Color lightWhiteColor () {
        return decodeColor("#F3FBFC");
    }

    public static Color decodeColor (String hex) {
        return Color.decode(hex);
    }
}
