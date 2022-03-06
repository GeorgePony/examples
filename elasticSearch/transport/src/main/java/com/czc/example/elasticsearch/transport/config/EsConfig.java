package com.czc.example.elasticsearch.transport.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.InetAddress;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/3/6下午9:52
 */
@Configuration
public class EsConfig {
    @Bean
    public TransportClient getClient(){

        Settings settings = Settings.builder().put("cluster.name","chen").build();
        try(TransportClient client = new PreBuiltTransportClient(settings)){
            TransportAddress firstAddress = new TransportAddress(InetAddress.getByName("127.0.0.1"),9300);
            TransportAddress secondAddress = new TransportAddress(InetAddress.getByName("127.0.0.1"),9301);
            TransportAddress thirdAddress = new TransportAddress(InetAddress.getByName("127.0.0.1"),9302);
            client.addTransportAddress(firstAddress);
            client.addTransportAddress(secondAddress);
            client.addTransportAddress(thirdAddress);
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
