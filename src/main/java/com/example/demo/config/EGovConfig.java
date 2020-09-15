package com.example.demo.config;

import egovframework.rte.fdl.cmmn.trace.LeaveaTrace;
import egovframework.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import egovframework.rte.fdl.cmmn.trace.handler.TraceHandler;
import egovframework.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import egovframework.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@Configuration
@ComponentScan(
        basePackages="egovframework",
        includeFilters={
                @ComponentScan.Filter(type=FilterType.ANNOTATION, value= Service.class)
                , @ComponentScan.Filter(type=FilterType.ANNOTATION, value= Repository.class)
        },
        excludeFilters={
                @ComponentScan.Filter(type=FilterType.ANNOTATION, value= Controller.class)
                , @ComponentScan.Filter(type=FilterType.ANNOTATION, value= Configuration.class)
        }
)
public class EGovConfig {

    @Bean // AntPathMatcher Bean 등록
    public AntPathMatcher antPathMater(){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher;
    }

    @Bean // DefaultTraceHandler Bean 등록
    public DefaultTraceHandler defaultTraceHandler(){
        DefaultTraceHandler defaultTraceHandler = new DefaultTraceHandler();
        return defaultTraceHandler;
    }

    @Bean // DefaultTraceHandleManager Bean 설정 및 등록
    public DefaultTraceHandleManager traceHandlerService(AntPathMatcher antPathMater,
                                                         DefaultTraceHandler defaultTraceHandler){
        DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
        defaultTraceHandleManager.setReqExpMatcher(antPathMater);
        defaultTraceHandleManager.setPatterns(new String[]{"*"});
        defaultTraceHandleManager.setHandlers(new TraceHandler[]{defaultTraceHandler});
        return defaultTraceHandleManager;
    }

    @Bean // LeaveaTrace Bean 설정 및 등록
    public LeaveaTrace leaveaTrace(DefaultTraceHandleManager traceHandlerService){
        LeaveaTrace leaveaTrace = new LeaveaTrace();
        leaveaTrace.setTraceHandlerServices(new TraceHandlerService[]{traceHandlerService});
        return leaveaTrace;
    }

//    @Bean
//    public ReloadableResourceBundleMessageSource messageSource(){
//        ReloadableResourceBundleMessageSource rrbms = new ReloadableResourceBundleMessageSource();
//        rrbms.setBasenames("classpath:egovframework/message/message-common"
//                , "classpath:egovframework/rte/fdl/idgnr/messages/idgnr"
//                , "classpath:egovframework/rte/fdl/property/messages/properties");
//        rrbms.setCacheSeconds(60);
//        return rrbms;
//    }
    /** Trace Config **/


}
