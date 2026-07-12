package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Map;

public final class KeyboardTypeTool implements FastTool {

    @Override
    public String name() {
        return "keyboard.type";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String text = (String) args.get("text");
        if (text == null) {
            return new SimpleObservation(false, "Text argument is missing.");
        }
        try {
            Robot robot = new Robot();
            // Basic AWT robot typing fallback for minimal demo
            for (char c : text.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                if (c == '*') keyCode = KeyEvent.VK_MULTIPLY;
                else if (c == '+') keyCode = KeyEvent.VK_ADD;
                else if (c == '-') keyCode = KeyEvent.VK_SUBTRACT;
                else if (c == '/') keyCode = KeyEvent.VK_DIVIDE;
                else if (c == '=') keyCode = KeyEvent.VK_EQUALS;
                else if (c == ':') keyCode = KeyEvent.VK_COLON;
                
                if (KeyEvent.CHAR_UNDEFINED != keyCode) {
                    try {
                        robot.keyPress(keyCode);
                        robot.keyRelease(keyCode);
                    } catch (IllegalArgumentException ignored) {
                    }
                }
            }
            return new SimpleObservation(true, "Typed: " + text);
        } catch (Exception e) {
            return new SimpleObservation(false, "Failed to type: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
