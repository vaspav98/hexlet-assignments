package exercise;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// BEGIN
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanPostProcessor.class);
    private Map<String, String> taggedClasses = new HashMap<>();


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            String loggingLevel = bean.getClass().getAnnotation(Inspect.class).level();
            taggedClasses.put(beanName, loggingLevel);
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (taggedClasses.containsKey(beanName)) {
            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                        String methodName = method.getName();
                        String methodArgs = Arrays.asList(args).toString();
                        String result = String.format("Was called method: %s() with arguments: %s",
                                methodName, methodArgs);
                        if (taggedClasses.get(beanName).equals("info")) {
                            LOGGER.info(result);
                        } else {
                            LOGGER.debug(result);
                        }
                        return method.invoke(bean, args);
                    });
        }
        return bean;
    }
}
// END
