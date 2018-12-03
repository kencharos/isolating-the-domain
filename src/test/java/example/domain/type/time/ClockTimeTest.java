package example.domain.type.time;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClockTimeTest {
    @Test
    @DisplayName("文字列変換が正しくできること")
    void translate() {
        final String text = "12:32";
        ClockTime ht = new ClockTime(text);
        assertEquals(text, ht.toString());
    }
}