package com.minliu.demo.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.pool2.PooledObject;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.jms.ConnectionFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: ActiveMQFactoryConfig <br>
 * date: 4:54 PM 19/02/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@Configuration
@ConditionalOnBean(ConnectionFactory.class)
public class ActiveMQFactoryConfig {

    @Configuration
    @ConditionalOnClass(CachingConnectionFactory.class)
    @ConditionalOnProperty(prefix = "spring.activemq.pool", name = "enabled", havingValue = "false", matchIfMissing = true)
    static class SimpleConnectionFactoryConfiguration {

        private final JmsProperties jmsProperties;

        private final ActiveMQProperties properties;

        private final List<ActiveMQConnectionFactoryCustomizer> connectionFactoryCustomizers;

        SimpleConnectionFactoryConfiguration(JmsProperties jmsProperties,
                                             ActiveMQProperties properties,
                                             ObjectProvider<ActiveMQConnectionFactoryCustomizer> connectionFactoryCustomizers) {
            this.jmsProperties = jmsProperties;
            this.properties = properties;
            this.connectionFactoryCustomizers = connectionFactoryCustomizers
                    .orderedStream().collect(Collectors.toList());
        }

        @Bean
        @ConditionalOnProperty(prefix = "spring.jms.cache", name = "enabled", havingValue = "true", matchIfMissing = true)
        public CachingConnectionFactory cachingJmsConnectionFactory() {
            JmsProperties.Cache cacheProperties = this.jmsProperties.getCache();
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
                    createConnectionFactory());
            connectionFactory.setCacheConsumers(cacheProperties.isConsumers());
            connectionFactory.setCacheProducers(cacheProperties.isProducers());
            connectionFactory.setSessionCacheSize(cacheProperties.getSessionCacheSize());
            return connectionFactory;
        }

        @Bean
        @ConditionalOnProperty(prefix = "spring.jms.cache", name = "enabled", havingValue = "false")
        public ActiveMQConnectionFactory jmsConnectionFactory() {
            return createConnectionFactory();
        }

        private ActiveMQConnectionFactory createConnectionFactory() {
            return new ActiveMQConnectionFactoryFactory(this.properties,
                    this.connectionFactoryCustomizers)
                    .createConnectionFactory(ActiveMQConnectionFactory.class);
        }


    }

    @Configuration
    @ConditionalOnClass({ JmsPoolConnectionFactory.class, PooledObject.class })
    static class PooledConnectionFactoryConfiguration {

        @Bean(destroyMethod = "stop")
        @ConditionalOnProperty(prefix = "spring.activemq.pool", name = "enabled", havingValue = "true", matchIfMissing = false)
        public JmsPoolConnectionFactory pooledJmsConnectionFactory(
                ActiveMQProperties properties,
                ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers) {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactoryFactory(
                    properties,
                    factoryCustomizers.orderedStream().collect(Collectors.toList()))
                    .createConnectionFactory(ActiveMQConnectionFactory.class);
            return new JmsPoolConnectionFactoryFactory(properties.getPool())
                    .createPooledConnectionFactory(connectionFactory);
        }

    }


}
