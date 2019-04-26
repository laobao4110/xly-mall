package com.xly.mall.common.base;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {
    public StringUtil() {
    }

    public static boolean isNull(String s) {
        return s == null;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static String replaceBlankToCustom(String str, String symbol) {
        if (StringUtils.isBlank(str)) {
            return str;
        } else {
            String dest = "";
            if (str != null) {
                Pattern p = Pattern.compile("\\s+|\t|\r|\n");
                Matcher m = p.matcher(str);
                dest = m.replaceAll(symbol);
            }

            if (String.valueOf(dest.charAt(0)).equals(symbol)) {
                dest = dest.substring(1, dest.length());
            }

            if (String.valueOf(dest.charAt(dest.length() - 1)).equals(symbol)) {
                dest = dest.substring(0, dest.length() - 1);
            }

            return dest;
        }
    }

    public static String replaceBlank1(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        } else {
            String dest = "";
            if (str != null) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(str);
                dest = m.replaceAll("");
            }

            return dest;
        }
    }

    public static String replaceTrnToBlank(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        } else {
            String dest = "";
            if (str != null) {
                Pattern p = Pattern.compile("\t|\r|\n");
                Matcher m = p.matcher(str);
                dest = m.replaceAll(" ");
            }

            return dest;
        }
    }

    public static String trimString(String str, String trimStr) {
        if (str != null && str.length() != 0 && trimStr != null && trimStr.length() != 0) {
            String regpattern = "[" + trimStr + "]*+";
            Pattern pattern = Pattern.compile(regpattern, 2);
            StringBuffer buffer = (new StringBuffer(str)).reverse();
            Matcher matcher = pattern.matcher(buffer);
            int epos;
            if (matcher.lookingAt()) {
                epos = matcher.end();
                str = (new StringBuffer(buffer.substring(epos))).reverse().toString();
            }

            matcher = pattern.matcher(str);
            if (matcher.lookingAt()) {
                epos = matcher.end();
                str = str.substring(epos);
            }

            return str;
        } else {
            return str;
        }
    }

    public static String toUpperCaseFirstChar(String str) {
        if (str != null && str.length() != 0) {
            return Character.isUpperCase(str.charAt(0)) ? str : Character.toUpperCase(str.charAt(0)) + str.substring(1);
        } else {
            return null;
        }
    }

    public static String toLowerCaseFirstChar(String str) {
        if (str != null && str.length() != 0) {
            return Character.isLowerCase(str.charAt(0)) ? str : Character.toLowerCase(str.charAt(0)) + str.substring(1);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        String a = "\"";
        String b = trimString(a, "\"");
        System.out.println(a);
        System.out.println(b);
    }
}
