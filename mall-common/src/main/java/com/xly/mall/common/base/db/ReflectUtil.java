package com.xly.mall.common.base.db;

import com.xly.mall.common.base.SystemException;

import java.lang.reflect.Field;

public class ReflectUtil {
    public ReflectUtil() {
    }

    public static Object getClassDeclaredField(Object obj, String filedText) throws Exception {
        Field field = obj.getClass().getDeclaredField(filedText);
        if (field == null) {
            return null;
        } else {
            field.setAccessible(true);
            return field.get(obj);
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);

            try {
                result = field.get(obj);
            } catch (IllegalArgumentException var5) {
                throw new SystemException("GetFieldValue error", var5);
            } catch (IllegalAccessException var6) {
                throw new SystemException("GetFieldValue error", var6);
            }
        }

        return result;
    }

    public static Object getSuperclassDeclaredField(Object obj, String filedText) throws Exception {
        Field field = obj.getClass().getSuperclass().getDeclaredField(filedText);
        if (field == null) {
            return null;
        } else {
            field.setAccessible(true);
            return field.get(obj);
        }
    }

    public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
        Field field = getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException var5) {
                throw new SystemException("GetFieldValue error", var5);
            } catch (IllegalAccessException var6) {
                throw new SystemException("GetFieldValue error", var6);
            }
        }

    }

    public static Field getField(Object obj, String fieldName) {
        Field field = null;
        Class clazz = obj.getClass();

        while(clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException var5) {
                clazz = clazz.getSuperclass();
            }
        }

        return field;
    }
}

