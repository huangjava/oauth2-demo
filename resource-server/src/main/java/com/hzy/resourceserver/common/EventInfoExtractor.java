package com.hzy.resourceserver.common;


import com.hzy.resourceserver.common.EventType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 事件时间与类型抽取器。
 *
 * @author Sand
 * @version 1.0
 * @created 2015.10.13 9:23
 */
@Component
@ConfigurationProperties(prefix = "ehr.pack-extractor.event")
public class EventInfoExtractor {

    //事件界定数据集
    private Map<String, String> dataSets = new HashMap<>();
    private List<String> clinic = new ArrayList<>();
    private Map<String, String>  fuzzyMatch =  new HashMap<>();

    public EventType getEventType(String dataSet){
        EventType eventType = null;
        if (dataSets.containsKey(dataSet)) {
            eventType = EventType.valueOf(dataSets.get(dataSet));
        }

        if (eventType == null) {
            for (String key:fuzzyMatch.keySet()) {
                if (dataSet.startsWith(key)) {
                    eventType = EventType.valueOf(fuzzyMatch.get(key));
                    break;
                }
            }
            System.out.println(eventType);
        }

        return eventType;
    }

    @PostConstruct
    public void postConstruct() {
        Set<String> keys = new HashSet<>(this.dataSets.keySet());
        for (String key : keys) {
            String value = this.dataSets.remove(key);
            key = key.replaceAll("^\\d{1,2}\\.", "");
            this.dataSets.put(key, value);
        }

        Set<String> key2s = new HashSet<>(this.fuzzyMatch.keySet());
        for (String key : key2s) {
            String value = this.fuzzyMatch.remove(key);
            key = key.replaceAll("^\\d{1,2}\\.", "");
            this.fuzzyMatch.put(key, value);
        }

    }

    public Map<String, String> getDataSets() {
        return this.dataSets;
    }

    public List<String> getClinic() {
        return clinic;
    }

    public Map<String, String> getFuzzyMatch() {
        return fuzzyMatch;
    }
}
