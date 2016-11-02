package com.sky.sdc.mobile.utilities;

/**
 * Created by msi15 on 01/11/2016.
 */
import java.net.ServerSocket;

public class Ports {

    public String getPort() throws Exception
    {
        ServerSocket socket = new ServerSocket(0);
        socket.setReuseAddress(true);
        String port = Integer.toString(socket.getLocalPort());
        socket.close();
        return port;
    }

}

