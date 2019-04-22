package com.xly.mall.common.base.result;

import com.xly.mall.common.domain.base.BusinessException;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.*;

public class CommonUtil {
    public static String OS = System.getProperty("os.name").toLowerCase();
    public static final String ENVIRONMENT = "ENVIRONMENT";
    public static final String ENVIRONMENT_PRE = "pre";
    public static final String ENVIRONMENT_GRAY = "gray";

    public CommonUtil() {
    }

    public static boolean isTrue(Boolean o) {
        return o == null ? false : o.booleanValue();
    }

    public static <E> boolean isListEmpty(List<E> list) {
        return list == null || list.isEmpty();
    }

    public static void assertNotBlank(String text, String message) {
        if (text == null || text.trim().isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static <T> void assertNotNull(T t, String message) {
        if (t == null) {
            throw new BusinessException(message);
        }
    }

    public static <T> void assertListNotNull(List<T> list, String message) {
        if (isListEmpty(list)) {
            throw new BusinessException(message);
        }
    }

    public static String formatNum(Object num) {
        DecimalFormat df = new DecimalFormat("#0.##########");
        return df.format(num);
    }

    public static <T> void assertNotEq(String arg1, String arg2, String message) {
        if (!arg1.equalsIgnoreCase(arg2)) {
            throw new BusinessException(message);
        }
    }

    public static void removeDuplicateWithOrder(List<String> list) {
        Set<String> set = new HashSet();
        List<String> newList = new ArrayList();
        Iterator iter = list.iterator();

        while(iter.hasNext()) {
            String element = (String)iter.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }

        list.clear();
        list.addAll(newList);
    }

    public static boolean isWindows() {
        return OS.indexOf("win") != -1;
    }

    public static String getListStr(List<String> list, String splitSign) {
        StringBuffer listSb = new StringBuffer();
        if (list != null && !list.isEmpty()) {
            int size = list.size();

            for(int i = 0; i < size; ++i) {
                listSb.append((String)list.get(i));
                int end = size - 1;
                if (i != end) {
                    listSb.append(splitSign);
                }
            }
        }

        return listSb.toString();
    }

    public static String getStringArrayStr(String[] strArray, String splitSign) {
        StringBuffer stringArraySb = new StringBuffer();
        if (strArray != null && strArray.length > 0) {
            int size = strArray.length;

            for(int i = 0; i < size; ++i) {
                stringArraySb.append(strArray[i]);
                int end = size - 1;
                if (i != end) {
                    stringArraySb.append(splitSign);
                }
            }
        }

        return stringArraySb.toString();
    }

    public static List<String> getList(String listStr, String splitSign) {
        List<String> list = new ArrayList();
        if (StringUtils.isNotBlank(listStr)) {
            String[] array = listStr.split(splitSign);
            String[] arr$ = array;
            int len$ = array.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String e = arr$[i$];
                list.add(e);
            }
        }

        return list;
    }

    public static String getEnvironment() {
        String environment = System.getenv("ENVIRONMENT");
        if (environment == null) {
            environment = System.getenv("ENVIRONMENT".toLowerCase());
        }

        return environment;
    }

    public static boolean isPreEnvironment() {
        String environment = getEnvironment();
        return StringUtils.isNotBlank(environment) && (environment.indexOf("pre") != -1 || environment.indexOf("pre".toUpperCase()) != -1);
    }

    public static boolean isGrayEnvironment() {
        String environment = getEnvironment();
        return StringUtils.isNotBlank(environment) && (environment.indexOf("gray") != -1 || environment.indexOf("gray".toUpperCase()) != -1);
    }

}
