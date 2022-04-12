package com.czc.example.drools.configure;

import com.czc.example.drools.entity.Order;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Configuration;


/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/4/11下午9:48
 */
@Configuration
public class KieConfig {


    public static void main(String[] args) {

        KieServices kieServices = KieServices.Factory.get();
        KieContainer container = kieServices.getKieClasspathContainer();
        KieSession session = container.newKieSession();
        Order order = new Order();
        order.setOriginalPrice(210D);
        session.insert(order);
        session.fireAllRules();
        session.dispose();
        System.out.println(1);
    }

}
