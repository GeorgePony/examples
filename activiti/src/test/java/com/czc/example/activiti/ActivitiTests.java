package com.czc.example.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/10/4下午3:15
 */
@SpringBootTest
public class ActivitiTests {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;


    private Deployment addDeployment(String path,String name){
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(path)
                .key(name)
                .name(name)
                .deploy();
        printDeployment(deployment);
        return deployment;
    }


    public void getDefinitions(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();

        list.forEach(this::printProcessDefinition);
    }


    public void deleteDefinition(String pid){
        repositoryService.deleteDeployment(pid,true);
    }

    @Test
    public void deleteAllDefinitions(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();
        list.forEach(e -> repositoryService.deleteDeployment(e.getDeploymentId(),true));

    }

    public void initProcessInstance(String key,String businessKey){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, businessKey);
        printProcessInstance(processInstance);
    }


    @Test
    public void test(){
        String key = "pro1";
        addDeployment("processes/Xiyou.bpmn",key);
        getDefinitions();
        initProcessInstance(key,"xiyouji1");
    }



    private void printProcessDefinition(ProcessDefinition pd){

        System.out.println("------流程定义--------");
        System.out.println("Name："+pd.getName());
        System.out.println("Key："+pd.getKey());
        System.out.println("ResourceName："+pd.getResourceName());
        System.out.println("DeploymentId："+pd.getDeploymentId());
        System.out.println("Version："+pd.getVersion());
    }

    private void printProcessInstance(ProcessInstance pi){
        System.out.println("--------流程实例------");
        System.out.println("ProcessInstanceId："+pi.getProcessInstanceId());
        System.out.println("ProcessDefinitionId："+pi.getProcessDefinitionId());
        System.out.println("isEnded"+pi.isEnded());
        System.out.println("isSuspended："+pi.isSuspended());
    }


    private void printDeployment(Deployment de){
        System.out.println("------ Deployment -------");
        System.out.println("Id:  " + de.getId());
        System.out.println("Key:  " + de.getKey());
        System.out.println("Name:" + de.getName());
        System.out.println("Version:" +de.getVersion());


    }

}
