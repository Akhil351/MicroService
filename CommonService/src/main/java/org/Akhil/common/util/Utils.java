package org.Akhil.common.util;

import java.text.MessageFormat;

public class Utils {
    public  static final String PAGE_NO="pageNo";
    public static final String PAGE_SIZE="pageSize";
    public static final String SEARCH_KEY="searchKey";
    public static String contains(Object expression){
        return MessageFormat.format("%{0}%",expression.toString());
    }
}
