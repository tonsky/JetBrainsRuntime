package java.awt.font;

import java.util.Collection;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

public final class FontFeature {
    public final int tag;
    public final int value;

    public FontFeature(int tag, int value) {
        this.tag = tag;
        this.value = value;
    }

    public static FontFeature fromString(String tag) {
        return new FontFeature(tagFromString(tag), 1);
    }

    private static final ByteBuffer EMPTY = ByteBuffer.allocateDirect(0);

    public static ByteBuffer toDirectByteBuffer(Collection<FontFeature> features) {
        if (features == null || features.size() == 0)
            return EMPTY;
        ByteBuffer bb = ByteBuffer.allocateDirect(features.size() * 16);
        bb.order(ByteOrder.nativeOrder());
        for (FontFeature f: features) {
            bb.putInt(f.tag);   // hb_tag_t      tag;
            bb.putInt(f.value); // uint32_t      value;
            bb.putInt(0);       // unsigned int  start;
            bb.putInt(-1);      // unsigned int  end;
        }
        return bb;
    }

    public static int tagFromString(String s) {
        if (s.length() != 4)
            throw new IllegalArgumentException("Expected 4-char string: " + s);
        return (s.charAt(0) & 0xFF) << 24 | (s.charAt(1) & 0xFF) << 16 | (s.charAt(2) & 0xFF) << 8 | (s.charAt(3) & 0xFF);
    }

    public static String stringFromTag(int tag) {
        byte[] bytes = new byte[] {
                (byte) ((tag >> 24) & 0xFF),
                (byte) ((tag >> 16) & 0xFF),
                (byte) ((tag >> 8) & 0xFF),
                (byte) (tag & 0xFF)
        };
        return new String(bytes);
    }

    public String toString() {
        return stringFromTag(tag) + "=" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontFeature that = (FontFeature) o;
        return tag == that.tag &&
                value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, value);
    }
}