package org.Akhil.common.util;

import java.text.MessageFormat;

public class Utils {
    public static String contains(Object expression){
        return MessageFormat.format("%{0}%",expression.toString());
    }
}
