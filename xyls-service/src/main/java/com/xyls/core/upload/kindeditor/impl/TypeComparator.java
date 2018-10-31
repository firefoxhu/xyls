package com.xyls.core.upload.kindeditor.impl;

import java.util.Comparator;
import java.util.Hashtable;

public class TypeComparator implements Comparator {
    @Override
    public int compare(Object a, Object b) {
        Hashtable hashA = (Hashtable) a;
        Hashtable hashB = (Hashtable) b;
        if (((Boolean) hashA.get("is_dir")) && !((Boolean) hashB.get("is_dir"))) {
            return -1;
        } else if (!((Boolean) hashA.get("is_dir")) && ((Boolean) hashB.get("is_dir"))) {
            return 1;
        } else {
            return ((String) hashA.get("filetype")).compareTo((String) hashB.get("filetype"));
        }
    }
}
