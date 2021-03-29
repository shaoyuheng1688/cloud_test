package com.raymon.frame.generator;

import org.guzz.util.LangUtil;

import java.net.InetAddress;

public abstract class AbstractUUIDGenerator {
    private static final int IP;
    private static short counter;
    private static final int JVM;

    protected int getJVM() {
        return JVM;
    }

    protected short getCount() {
        Class var1 = org.guzz.id.AbstractUUIDGenerator.class;
        synchronized(org.guzz.id.AbstractUUIDGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }

            short var10000 = counter;
            counter = (short)(var10000 + 1);
            return var10000;
        }
    }

    protected int getIP() {
        return IP;
    }

    protected short getHiTime() {
        return (short)((int)(System.currentTimeMillis() >>> 32));
    }

    protected int getLoTime() {
        return (int)System.currentTimeMillis();
    }

    static {
        int ipadd;
        try {
            ipadd = LangUtil.bytes2Int(InetAddress.getLocalHost().getAddress());
        } catch (Exception var2) {
            ipadd = 0;
        }

        IP = ipadd;
        counter = 0;
        JVM = (int)(System.currentTimeMillis() >>> 8);
    }
}
