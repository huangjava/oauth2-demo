package com.hzy.resourceserver.service;

import com.hzy.resourceserver.common.EventType;
import com.hzy.resourceserver.common.EventInfoExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author HZY
 * @created 2018/9/18 11:42
 */
public class DemoConfigService implements ClientDetailsService {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ApplicationContext context;

    public Map<String,String> getMapProprites(){
        EventInfoExtractor bean = this.context.getBean(EventInfoExtractor.class);
        Map<String, String> dataSets = bean.getDataSets();
        return dataSets;
    }

    public List<String> getListProprites(){
        EventInfoExtractor bean = this.context.getBean(EventInfoExtractor.class);
        List<String> dataSets = bean.getClinic();
        return dataSets;
    }


    public EventType getEventType(String dateSet){
        EventInfoExtractor bean = this.context.getBean(EventInfoExtractor.class);
        EventType eventType = bean.getEventType(dateSet);
        return eventType;
    }




    @Override
    public ClientDetails loadClientByClientId(String applyName) throws ClientRegistrationException {
        //证client是否存在 ，根据需求写sql
//        Map clientMap = applyService.findApplyById(s);
//        if(clientMap == null) {
//            throw new ClientRegistrationException("应用" + applyName + "不存在!");
//        }

//        MyJdbcClientDetailsService jdbcClientDetailsService= new MyJdbcClientDetailsService(dataSource, "authentication");
        JdbcClientDetailsService jdbcClientDetailsService= new JdbcClientDetailsService(dataSource);
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(applyName);

        return clientDetails;
    }
}
