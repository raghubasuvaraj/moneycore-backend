//package com.moneycore.controller;
//
//import com.moneycore.service.SwaggerServicesConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Primary
//@EnableAutoConfiguration
//public class DocumentationController implements SwaggerResourcesProvider {
//
//   @Autowired
//   private SwaggerServicesConfig swaggerServiceList;
//
//   @Override
//   public List get() {
//      List<SwaggerResource> resources = new ArrayList<>();
//      List<SwaggerServicesConfig.SwaggerServices> services = swaggerServiceList.getServices();
//
//      services.forEach(service -> {
//         resources.add(swaggerResource(service.getName(),service.getUrl(), service.getVersion()));
//      });
//      return resources;
//   }
//
//   private SwaggerResource swaggerResource(String name, String location, String version) {
//      SwaggerResource swaggerResource = new SwaggerResource();
//      swaggerResource.setName(name);
//      swaggerResource.setLocation(location);
//      swaggerResource.setSwaggerVersion(version);
//      return swaggerResource;
//   }
//
//}