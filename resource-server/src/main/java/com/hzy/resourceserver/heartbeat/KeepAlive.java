package com.hzy.resourceserver.heartbeat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HZY
 * @created 2018/9/30 16:28
 */
public class KeepAlive implements Serializable {

    private static final long serialVersionUID = -2813120366138988480L;
    private String userName;
    private String telePhone;

    public KeepAlive(String userName, String telePhone) {
        this.userName = userName;
        this.telePhone = telePhone;
    }

//    /* 覆盖该方法，仅用于测试使用。
//         * @see java.lang.Object#toString()
//         */
//    @Override
//    public String toString() {
//        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t维持连接包";
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }
}
