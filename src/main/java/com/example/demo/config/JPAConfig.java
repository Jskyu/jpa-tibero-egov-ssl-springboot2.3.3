package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.*;
import java.util.Properties;

/**
 * @class JPAConfig
 * 설명 : JPA 와 관련된 셋팅을 수동으로 하기 위한 Class 이다.
 */
@Configuration
@RequiredArgsConstructor
public class JPAConfig {

    //DataSource 객체 생성, 의존성 주입
    private final DataSource dataSource;

    /**
     * @return EntityManagerFactory
     * @method entityManagerFactory
     * 설명 : EntityManagerFactory 를 수동으로 설정해주는 Method 이다.
     */
    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        //ddl-auto 의 사용 여부를 결정한다.
        vendorAdapter.setGenerateDdl(false);

        //EntityManager 에 대한 FactoryBean 설정
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        //Hibernate vendor 와 JPA 간의 Adapter 를 설정한다.
        factory.setJpaVendorAdapter(vendorAdapter);
        //Entity 클래스의 위치를 설정한다.
        factory.setPackagesToScan("com.example.demo.vo");
        //DataSource 를 주입한다.
        factory.setDataSource(dataSource);
        //dialect 를 TiberoDialect 로 주입한다.
        factory.getJpaPropertyMap().put("hibernate.dialect", TiberoDialect.class.getName());
        //위 설정이 다 끝난다면 모든 설정을 주입한다.
        factory.afterPropertiesSet();

        // 설정한 내용을 EntityManagerFactory 로 반환한다.
        return factory.getObject();
    }

    /**
     * @method transactionManager
     * @return PlatformTransactionManager
     * 설명 : JPA 를 지원하는 TransactionManager 에 위 entityManagerFactory 메서드에서 설정한
     *       EntityManagerFactory 를 주입하여 등록한다.
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        // JPA 를 지원하는 TransactionManager 생성
        JpaTransactionManager txManager = new JpaTransactionManager();
        // TransactionManager 에 EntityManagerFactory 주입 후 반환
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
}