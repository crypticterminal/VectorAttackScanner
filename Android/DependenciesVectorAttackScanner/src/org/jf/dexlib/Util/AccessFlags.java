/*
 * [The "BSD licence"]
 * Copyright (c) 2009 Ben Gruver
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.dexlib.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum AccessFlags
{
    PUBLIC(0x1, "public", true, true, true),
    PRIVATE(0x2, "private", true, true, true),
    PROTECTED(0x4, "protected", true, true, true),
    STATIC(0x8, "static", true, true, true),
    FINAL(0x10, "final", true, true, true),
    SYNCHRONIZED(0x20, "synchronized", false, true, false),
    VOLATILE(0x40, "volatile", false, false, true),
    BRIDGE(0x40, "bridge", false, true, false),
    TRANSIENT(0x80, "transient", false, false, true),
    VARARGS(0x80, "varargs", false, true, false),
    NATIVE(0x100, "native", false, true, false),
    INTERFACE(0x200, "interface", true, false, false),
    ABSTRACT(0x400, "abstract", true, true, false),
    STRICTFP(0x800, "strictfp", false, true, false),
    SYNTHETIC(0x1000, "synthetic", true, true, true),
    ANNOTATION(0x2000, "annotation", true, false, false),
    ENUM(0x4000, "enum", true, false, true),
    CONSTRUCTOR(0x10000, "constructor", false, true, false),
    DECLARED_SYNCHRONIZED(0x20000, "declared-synchronized", false, true, false);

    private int value;
    private String accessFlagName;
    private boolean validForClass;
    private boolean validForMethod;
    private boolean validForField;

    private static HashMap<String, AccessFlags> accessFlagsByName;

    static {
        accessFlagsByName = new HashMap<String, AccessFlags>();
        for (AccessFlags accessFlag: AccessFlags.values()) {
            accessFlagsByName.put(accessFlag.accessFlagName, accessFlag);
        }
    }

    private AccessFlags(int value, String accessFlagName, boolean validForClass, boolean validForMethod,
                        boolean validForField) {
        this.value = value;
        this.accessFlagName = accessFlagName;
        this.validForClass = validForClass;
        this.validForMethod = validForMethod;
        this.validForField = validForField;
    }

    public static AccessFlags[] getAccessFlagsForClass(int accessFlagValue) {
        int size = 0;
        for (AccessFlags accessFlag: AccessFlags.values()) {
            if (accessFlag.validForClass && (accessFlagValue & accessFlag.value) != 0) {
                size++;
            }
        }

        AccessFlags[] accessFlags = new AccessFlags[size];
        int accessFlagsPosition = 0;
        for (AccessFlags accessFlag: AccessFlags.values()) {
            if (accessFlag.validForClass && (accessFlagValue & accessFlag.value) != 0) {
                accessFlags[accessFlagsPosition++] = accessFlag;
            }
        }
        return accessFlags;
    }

    private static String formatAccessFlags(AccessFlags[] accessFlags) {
        int size = 0;
        for (AccessFlags accessFlag: accessFlags) {
            size += accessFlag.toString().length() + 1;
        }

        StringBuilder sb = new StringBuilder(size);
        for (AccessFlags accessFlag: accessFlags) {
            sb.append(accessFlag.toString());
            sb.append(" ");
        }
        if (accessFlags.length > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    public static String formatAccessFlagsForClass(int accessFlagValue) {
        return formatAccessFlags(getAccessFlagsForClass(accessFlagValue));
    }

    public static AccessFlags[] getAccessFlagsForMethod(int accessFlagValue) {
        int size = 0;
        for (AccessFlags accessFlag: AccessFlags.values()) {
            if (accessFlag.validForMethod && (accessFlagValue & accessFlag.value) != 0) {
                size++;
            }
        }

        AccessFlags[] accessFlags = new AccessFlags[size];
        int accessFlagsPosition = 0;
        for (AccessFlags accessFlag: AccessFlags.values()) {
            if (accessFlag.validForMethod && (accessFlagValue & accessFlag.value) != 0) {
                accessFlags[accessFlagsPosition++] = accessFlag;
            }
        }
        return accessFlags;
    }

    public static String formatAccessFlagsForMethod(int accessFlagValue) {
        return formatAccessFlags(getAccessFlagsForMethod(accessFlagValue));
    }

    public static AccessFlags[] getAccessFlagsForField(int accessFlagValue) {
        int size = 0;
        for (AccessFlags accessFlag: AccessFlags.values()) {
            if (accessFlag.validForField && (accessFlagValue & accessFlag.value) != 0) {
                size++;
            }
        }

        AccessFlags[] accessFlags = new AccessFlags[size];
        int accessFlagsPosition = 0;
        for (AccessFlags accessFlag: AccessFlags.values()) {
            if (accessFlag.validForField && (accessFlagValue & accessFlag.value) != 0) {
                accessFlags[accessFlagsPosition++] = accessFlag;
            }
        }
        return accessFlags;
    }

    public static String formatAccessFlagsForField(int accessFlagValue) {
        return formatAccessFlags(getAccessFlagsForField(accessFlagValue));
    }

    public static AccessFlags getAccessFlag(String accessFlag) {
        return accessFlagsByName.get(accessFlag);
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return accessFlagName;
    }
}
