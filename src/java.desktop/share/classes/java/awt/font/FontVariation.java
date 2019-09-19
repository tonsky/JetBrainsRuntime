package java.awt.font;

import java.util.Collection;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

public final class FontVariation {
    public final int tag;
    public final float value;

    public FontVariation(int tag, float value) {
        this.tag = tag;
        this.value = value;
    }

    public static FontVariation fromString(String tag, float value) {
        return new FontVariation(FontFeature.tagFromString(tag), value);
    }

    private static final ByteBuffer EMPTY = ByteBuffer.allocateDirect(0);

    public static ByteBuffer toDirectByteBuffer(Collection<FontVariation> variations) {
        if (variations == null || variations.size() == 0)
            return EMPTY;
        ByteBuffer bb = ByteBuffer.allocateDirect(variations.size() * 8);
        bb.order(ByteOrder.nativeOrder());
        for (FontVariation f: variations) {
            bb.putInt(f.tag);     // hb_tag_t      tag;
            bb.putFloat(f.value); // float         value;
        }
        return bb;
    }
    
    public String toString() {
        return FontFeature.stringFromTag(tag) + "=" + value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontVariation that = (FontVariation) o;
        return tag == that.tag &&
                Float.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, value);
    }
}