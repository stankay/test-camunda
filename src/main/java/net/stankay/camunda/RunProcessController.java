package net.stankay.camunda;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Manually run process. curl -v "http://localhost:8080/run/hello?whereTo=europe&foo=bar"
 *
 * All the vars from query string will passed as variables to camunda process
 *
 * https://docs.camunda.org/manual/7.11/user-guide/process-engine/process-engine-api/
 * https://camunda.com/best-practices/managing-the-task-lifecycle/#assigning-tasks
 *
 * @author Michal Stankay
 */
@RestController
@RequestMapping("/run/{processName}")
public class RunProcessController {

    @Autowired RuntimeService runtimeService;

    @GetMapping()
    public String get(@PathVariable(value="processName", required = false) Optional<String> processName,
            HttpServletRequest request
            ) {

        Enumeration<String> it = request.getParameterNames();

        Map<String, Object> vars = new HashMap<>();

        while (it.hasMoreElements()) {
            String parameterName = it.nextElement();
            vars.put(parameterName, request.getParameter(parameterName));
        }

        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processName.get(), vars);

        String output = String.format("Starting process %s (instance %s) with variables %s\n",
                processName.get(),
                pi.getProcessInstanceId(),
                vars);
        System.out.println(output);
        return output;
    }
}
