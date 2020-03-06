package net.stankay.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class HelloDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("----");
        System.out.printf("I am inside process %s (%s) doing task %s\n",
                execution.getProcessDefinitionId(),
                execution.getProcessInstanceId(),
                execution.getCurrentActivityName());
        System.out.println("Variables are " + execution.getVariables());
        System.out.println("----");

        execution.setVariable("lastTask", execution.getCurrentActivityName());
    }
}