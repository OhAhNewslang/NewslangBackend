//package ohai.newslang.configuration.config;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
//
//public class IntrospectorUtil {
//    private static final String HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME = "mvcHandlerMappingIntrospector";
//
//    private IntrospectorUtil() {
//    }
//
//    public static HandlerMappingIntrospector getIntrospector(HttpSecurity http) {
//        ApplicationContext context = http.getSharedObject(ApplicationContext.class);
//        return context.getBean(HANDLER_MAPPING_INTROSPECTOR_BEAN_NAME, HandlerMappingIntrospector.class);
//    }
//}