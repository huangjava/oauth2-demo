package com.hzy.resourceserver.heartbeat;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HZY
 * @created 2018/9/30 15:39
 */
public class Server {
    public static Map<String,Long> accountMap = new HashMap<>();
    /**
     * 要处理客户端发来的对象，并返回一个对象，可实现该接口。
     */
    public interface ObjectAction{
        Object doAction(Object rev, Server server);
    }

    public static final class DefaultObjectAction implements ObjectAction{
        @Override
        public Object doAction(Object rev, Server server) {
            System.out.println("处理并返回："+rev);
            return rev;
        }
    }

    public static void main(String[] args) {
        int port = 65432;
        Server server = new Server(port);
        server.start();
    }

    private int port;
    private volatile boolean running=false;
    private long receiveTimeDelay=3000;
    private ConcurrentHashMap<Class, ObjectAction> actionMapping = new ConcurrentHashMap<Class,ObjectAction>();
    private Thread connWatchDog;

    public Server(int port) {
        this.port = port;
    }

    public void start(){
        if(running) {
            return;
        }
        running=true;
        connWatchDog = new Thread(new ConnWatchDog());
        connWatchDog.start();
    }

    @SuppressWarnings("deprecation")
    public void stop(){
        if(running) {
            running = false;
        }
        if(connWatchDog!=null) {
            connWatchDog.stop();
        }
    }

    public void addActionMap(Class<Object> cls,ObjectAction action){
        actionMapping.put(cls, action);
    }

    class ConnWatchDog implements Runnable{
        @Override
        public void run(){
            try {
                ServerSocket ss = new ServerSocket(port,5);
                while(running){
                    Socket s = ss.accept();
                    new Thread(new SocketAction(s)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Server.this.stop();
            }

        }
    }

    class SocketAction implements Runnable{
        Socket s;
        boolean run=true;
        long lastReceiveTime = System.currentTimeMillis();
        public SocketAction(Socket s) {
            this.s = s;
        }
        @Override
        public void run() {
            while(running && run){
                if(System.currentTimeMillis()-lastReceiveTime>receiveTimeDelay){
                    overThis();
                }else{
                    try {
                        InputStream in = s.getInputStream();
                        if(in.available()>0){
                            ObjectInputStream ois = new ObjectInputStream(in);
                            KeepAlive obj = (KeepAlive) ois.readObject();
                            lastReceiveTime = System.currentTimeMillis();
                            accountMap.put(obj.getUserName(),lastReceiveTime);
                            System.out.println("接收：\t"+obj);
                            ObjectAction oa = actionMapping.get(obj.getClass());
                            oa = oa==null?new DefaultObjectAction():oa;
                            Object out = oa.doAction(obj,Server.this);
                            if(out!=null){
                                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                                oos.writeObject(out);
                                oos.flush();
                            }
                        }else{
                            Thread.sleep(10);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        overThis();
                    }
                }
            }
        }

        private void overThis() {
            if(run) {
                run = false;
            }
            if(s!=null){
                try {
                    //TODO 获取离线用户
                    Iterator<String> iterator = accountMap.keySet().iterator();
                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        long time = accountMap.get(key);

                    }
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("关闭："+s.getRemoteSocketAddress());
        }

    }

}


