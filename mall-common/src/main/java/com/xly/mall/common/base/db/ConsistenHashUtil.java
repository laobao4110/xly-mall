package com.xly.mall.common.base.db;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistenHashUtil<T> {
    private int numberOfReplicas = 160;
    private final SortedMap<Long, T> circle = new TreeMap();

    public ConsistenHashUtil(Collection<T> nodes) {
        Iterator i$ = nodes.iterator();

        while(i$.hasNext()) {
            T node = (T)i$.next();
            this.add(node);
        }

    }

    public ConsistenHashUtil(Collection<T> nodes, int nodeCopies) {
        this.numberOfReplicas = 8 * nodeCopies;
        Iterator i$ = nodes.iterator();

        while(i$.hasNext()) {
            T node = (T)i$.next();
            this.add(node);
        }

    }

    public void add(T node) {
        for(int i = 0; i < this.numberOfReplicas / 4; ++i) {
            byte[] digest = this.computeMd5(node.toString() + "-" + i);

            for(int h = 0; h < 4; ++h) {
                this.circle.put(this.defaultHash(digest, h), node);
            }
        }

    }

    public void remove(T node) {
        for(int i = 0; i < this.numberOfReplicas / 4; ++i) {
            byte[] digest = this.computeMd5(node.toString() + i);

            for(int h = 0; h < 4; ++h) {
                this.circle.remove(this.defaultHash(digest, h));
            }
        }

    }

    public T get(Object key) {
        if (this.circle.isEmpty()) {
            return null;
        } else {
            byte[] digest = this.computeMd5(key.toString());
            long hash = this.defaultHash(digest, 0);
            if (!this.circle.containsKey(hash)) {
                SortedMap<Long, T> tailMap = this.circle.tailMap(hash);
                hash = (tailMap.isEmpty() ? (Long)this.circle.firstKey() : (Long)tailMap.firstKey()).longValue();
            }

            return this.circle.get(hash);
        }
    }

    public long defaultHash(byte[] digest, int nTime) {
        long rv = (long)(digest[3 + nTime * 4] & 255) << 24 | (long)(digest[2 + nTime * 4] & 255) << 16 | (long)(digest[1 + nTime * 4] & 255) << 8 | (long)(digest[0 + nTime * 4] & 255);
        return rv & 4294967295L;
    }

    public byte[] computeMd5(String k) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var6) {
            throw new RuntimeException("MD5 not supported", var6);
        }

        md5.reset();
        Object var3 = null;

        byte[] keyBytes;
        try {
            keyBytes = k.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var5) {
            throw new RuntimeException("Unknown string :" + k, var5);
        }

        md5.update(keyBytes);
        return md5.digest();
    }

    private void printNodes() {
        System.out.println("size=" + this.circle.size());
        System.out.println(this.circle);
    }

    public static void main(String[] args) {
        List<String> nodes = new ArrayList();
        nodes.add("1");
        nodes.add("2");
        nodes.add("3");
        nodes.add("4");
        ConsistenHashUtil<String> consistenHashUtil = new ConsistenHashUtil(nodes);
        consistenHashUtil.printNodes();
        String a = "TTT";
        String B = (String)consistenHashUtil.get(a);
        System.out.println(B);
    }
}

