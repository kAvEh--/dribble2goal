package ir.eynakgroup.dribble2goal.Util;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

/**
 * Created by Eynak_PC2 on 2/21/2017.
 */

public class DirectionalInput extends TextField {
    private final boolean rtl;
    private boolean nflp = false;

    public DirectionalInput(String txt, Skin skin) {
        this(txt, skin, true);// this last var should be true if your default language is RTL
    }

    public DirectionalInput(String txt, Skin skin, boolean rtl) {
        super(RTLFlipper.flip(txt), skin);
        this.rtl = rtl;

        addListener(new InputListener() {
            boolean r, l;
            String chunk = getText();
            int oc;

            @Override // TODO: fix spaces after RTL text
            // TODO: remember what directionality I last typed if I didn't
            // click to move the cursor and use that as current
            public boolean keyTyped(InputEvent e, char c) {
                if (c == '\b') {
                    if (chunk.length() == 0)
                        return true;

                    if (chunk != text)
                        cursor++;// only add if we removed a char

                    r = chunk.length() > cursor && RTLFlipper.isRTL(RTLFlipper.getFirstChunk(chunk.substring(cursor), RTLFlipper.RTL), DirectionalInput.this.rtl);
                    l = cursor > 0 && RTLFlipper.LTR(chunk.charAt(cursor - 1));

                    if (cursor == chunk.length() && RTLFlipper.RTL(chunk.charAt(cursor - 1))) {
                        oc = cursor;
                        set(chunk);
                        cursor = oc;
                    }
                    else if ((l && !r) || ((l || !r) && !DirectionalInput.this.rtl)) {
                        if (cursor > 0)
                            cursor--;// everything is the same
                    }
                    else {
                        oc = cursor;
                        set(chunk.substring(0, oc) + chunk.substring(oc + 1));
                        cursor = oc;
                    }
                }
                else if (c == 127) {// DELETE
                    if (chunk.length() == 0)
                        return true;

                    r = chunk.length() > cursor && RTLFlipper.isRTL(RTLFlipper.getFirstChunk(chunk.substring(cursor), RTLFlipper.RTL), DirectionalInput.this.rtl);
                    l = cursor > 0 && RTLFlipper.LTR(chunk.charAt(cursor - 1));

                    if ((!l || DirectionalInput.this.rtl) && r) {
                        if (cursor > 0) {
                            oc = cursor - 1;
                            set(chunk.substring(0, oc) + chunk.substring(oc + 1));
                            cursor = oc;
                        }
                        else
                            set(chunk);
                    }
                }
                else if (RTLFlipper.RTL(c))
                    cursor--;
                else if (text.length() > cursor && RTLFlipper.LTR(c) && RTLFlipper.isRTL(chunk = RTLFlipper.getFirstChunk(text.substring(cursor), RTLFlipper.RTL), true)) {
                    oc = cursor;
                    set(text.substring(0, cursor - 1) + text.substring(cursor) + c);
                    cursor = oc + chunk.length();
                }

                chunk = text;// to help with '\b'

                return true;
            }
        });
    }

    @Override
    public void appendText(String str) {
        // TODO fix this to support RTL
        super.appendText(RTLFlipper.flip(str));
    }

    private void set(String str) {
        nflp = true;
        setText(str);
        nflp = false;
    }

//    @Override
//    void paste(String content, boolean fireChangeEvent) {
//        super.paste(nflp ? content : RTLFlipper.flip(content), fireChangeEvent);
//    }

//    @Override
//    public void copy() {
//        if (hasSelection && !passwordMode)
//            clipboard.setContents(RTLFlipper.flip(text.substring(Math.min(cursor, selectionStart), Math.max(cursor, selectionStart))));
//    }
}