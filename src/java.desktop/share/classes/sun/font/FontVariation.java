package sun.font;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class FontVariation {
    public final int tag;
    public final float value;

    public FontVariation(int tag, float value) {
        this.tag = tag;
        this.value = value;
    }

    public static FontVariation fromString(String tag, float value) {
        return new FontVariation(FontUtilities.hbTagFromString(tag), value);
    }

    public static ByteBuffer toDirectByteBuffer(List<FontVariation> features) {
        ByteBuffer bb = ByteBuffer.allocateDirect(features.size() * 8);
        bb.order(ByteOrder.nativeOrder());
        for (FontVariation f: features) {
            bb.putInt(f.tag);     // hb_tag_t      tag;
            bb.putFloat(f.value); // float         value;
        }
        return bb;
    }
}