package net.yeyito.storage.client_and_server;

import java.util.HashSet;
import java.util.Set;

public class DashingInfo {
    public static final int dashTickForgiveness = 3;
    public static final int dashTickMaxTime = 80;

    // Client and Server each access their own separate copies:
    public static final Set<Integer> critIDs = new HashSet<Integer>();
}
