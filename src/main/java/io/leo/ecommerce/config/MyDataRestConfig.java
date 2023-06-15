package io.leo.ecommerce.config;

import io.leo.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    @Value("${allowed.origins}") //read from application.properties
    private String theAllowedOrigins;
    private EntityManager entityManager;

    public MyDataRestConfig (EntityManager theEntityManager){
        entityManager = theEntityManager;
    }


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        //Disable POST, DELETE, UPDATE for the following entities
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};
        disableCUDRequests(Product.class,config, theUnsupportedActions);
        disableCUDRequests(ProductCategory.class,config, theUnsupportedActions);
        disableCUDRequests(Country.class,config, theUnsupportedActions);
        disableCUDRequests(State.class,config, theUnsupportedActions);
        disableCUDRequests(Order.class,config, theUnsupportedActions);

        //call an internal helper method
        exposeIds (config);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private static void disableCUDRequests(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        //disable CUD for products
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

    }

    private void exposeIds(RepositoryRestConfiguration config) {

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
        ArrayList<Class> entityClasses = new ArrayList<>();

       for(EntityType tempEntityType : entities ){
           entityClasses.add(tempEntityType.getJavaType());
       }
       Class[] domainTypes = entityClasses.toArray(new Class[0]);
       config.exposeIdsFor(domainTypes);
    }
}
