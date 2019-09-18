package sun.font;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class FontFeature {
    public final int tag;
    public final int value;

    public FontFeature(int tag, int value) {
        this.tag = tag;
        this.value = value;
    }

    public static FontFeature fromString(String tag) {
        return new FontFeature(FontUtilities.hbTagFromString(tag), 1);
    }

    public static ByteBuffer toDirectByteBuffer(List<FontFeature> features) {
        ByteBuffer bb = ByteBuffer.allocateDirect(features.size() * 16);
        bb.order(ByteOrder.nativeOrder());
        for (FontFeature f: features) {
            bb.putInt(f.tag);       // hb_tag_t      tag;
            bb.putInt(f.value);     // uint32_t      value;
            bb.putLong(0xFFFFFFFF); // unsigned int  start;
                                    // unsigned int  end;
        }
        return bb;
    }
}