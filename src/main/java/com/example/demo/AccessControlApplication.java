package com.example.demo;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
@EnableScheduling
public class AccessControlApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccessControlApplication.class, args);

//    ApplicationContext applicationContext = SpringApplication.run(AccessControlApplication.class, args);
//    for (String name : applicationContext.getBeanDefinitionNames()) {
//      System.out.println(name);
//    }
  }

  @Bean
  public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
      }
    };
    tomcat.addAdditionalTomcatConnectors(this.httpConnector());
    return tomcat;
  }

  @Value("${server.https-port}")
  private Integer httpsPort;

  @Value("${server.http-port}")
  private Integer httpPort;

  @Bean
  public Connector httpConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(this.httpPort);
    connector.setSecure(false);
    connector.setRedirectPort(this.httpsPort);
    return connector;
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  }

}
