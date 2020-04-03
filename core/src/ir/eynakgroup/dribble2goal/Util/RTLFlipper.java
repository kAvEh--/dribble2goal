package ir.eynakgroup.dribble2goal.Util;

/**
 * Created by Eynak_PC2 on 2/21/2017.
 */

import java.util.ArrayList;

public class RTLFlipper {
    public static final byte LTR = 0, RTL = 1;

    public static boolean LTR(char c) {
        return Character.getDirectionality(c) == Character.DIRECTIONALITY_LEFT_TO_RIGHT || (c >= '0' && c <= '9');
    }

    public static boolean RTL(char c) {
        return Character.getDirectionality(c) == Character.DIRECTIONALITY_RIGHT_TO_LEFT || Character.getDirectionality(c) == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

    public static String getFirstChunk(CharSequence str, byte def) {
        StringBuffer res = new StringBuffer();
        char c = str.charAt(0);
        byte t;
        if (LTR(c))
            t = LTR;
        else if (RTL(c))
            t = RTL;
        else
            t = def;

        out:
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (res.length() > 0 && (c == ' ' || res.charAt(0) == ' '))
                break;

            switch (t) {
                case LTR:
                    if (RTL(c))
                        break out;
                    break;
                case RTL:
                    if (LTR(c))
                        break out;
                    break;
            }
            res.append(c);
        }

        return res.toString();
    }

    private static String[] getChunks(String str, byte def) {
        ArrayList<String> res = new ArrayList();
        String s = new String(str), tmp;
        byte l = def;
        while (s.length() > 0) {
            tmp = getFirstChunk(s, l);
            res.add(tmp);
            s = s.substring(tmp.length());
            if (LTR(tmp.charAt(0)))
                l = LTR;
            else if (RTL(tmp.charAt(0)))
                l = RTL;
        }
        return res.toArray(new String[res.size()]);
    }

    public static boolean isRTL(String str) {
        return isRTL(str, false);// this last var should be true if your default language is RTL
    }

    public static boolean isRTL(String str, boolean base) {
        for (char c : str.toCharArray()) {
            if (RTL(c))
                return true;
            else if (LTR(c))
                return false;
        }
        return base;
    }

    // this just flips text...
    public static String flip(String s, boolean brackets) {
        String res = new StringBuilder(s).reverse().toString();
        if (brackets) {
            res = res.replace('(', '\0').replace(')', '(').replace('\0', ')')//
                    .replace('[', '\0').replace(']', '[').replace('\0', ']')//
                    .replace('<', '\0').replace('>', '<').replace('\0', '>');
        }
        return res;
    }

    // this does the RTL flips
    public static String flip(String str) {
        if (str == null)
            return null;

        StringBuilder res = new StringBuilder();
        for (String s : str.split("\n"))
            res.append(flipLine(s)).append('\n');
        res.setLength(res.length() - 1);// remove last '\n'
        return res.toString();
    }

    // TODO: if final punctuation marks happens when string is RTL-LTR-MRK
    // we assume based off of our current language's directionality
    private static String flipLine(String str) {
        if (str.length() == 0)
            return "";

        boolean rtl = isRTL(str), r;
        StringBuilder res = new StringBuilder();
        String[] cs = getChunks(str, rtl ? RTL : LTR);
        for (String s : cs) {
            r = isRTL(s, rtl);
            if (s != cs[cs.length - 1] || r == rtl)
                res.insert(end(res, rtl), r ? flip(s, true) : s);
            else {
                int i = s.length();
                out:
                for (; i > 0; i--) {
                    switch (Character.getDirectionality(s.charAt(i - 1))) {
                        case Character.DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR:
                        case Character.DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR:
                        case Character.DIRECTIONALITY_COMMON_NUMBER_SEPARATOR:
                        case Character.DIRECTIONALITY_OTHER_NEUTRALS:
                            break;
                        default:
                            break out;
                    }
                }
                String _s = s.substring(0, i);
                res.insert(end(res, rtl), r ? flip(_s, false) : _s);
                _s = s.substring(i);
                res.insert(end(res, rtl), r ? flip(_s, false) : _s);
            }
        }

        return res.toString();
    }

    private static int end(StringBuilder s, boolean rtl) {
        return (rtl ? 0 : s.length());
    }
}