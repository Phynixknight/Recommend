package cn.sssyin.server.recommend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import cn.sssyin.modules.recommend.common.Manager;

public class Bussiness_Recommend_Server {
//	protected static Logger logger = LoggerFactory.getLogger(Bussiness_Recommend_Server.class);

    public static void main(String[] args){
        // 添加 ResourceClass
        List<Class<?>> resourceClassList = new ArrayList<Class<?>>();
        resourceClassList.add(ProductServiceImpl.class);

        // 添加 ResourceProvider
        List<ResourceProvider> resourceProviderList = new ArrayList<ResourceProvider>();
        try {
			resourceProviderList.add(new SingletonResourceProvider(new ProductServiceImpl()));
		} catch (IOException e) {
//			logger.error("server stopped!");
//			logger.error(e.getMessage());
			System.exit(0);
		}

        // 添加 Provider
        List<Object> providerList = new ArrayList<Object>();
        providerList.add(new JacksonJsonProvider());

        // 发布 REST 服务
        //"http://localhost:9090/rec/bus"
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setAddress(Manager.Server_Domain);
        factory.setResourceClasses(resourceClassList);
        factory.setResourceProviders(resourceProviderList);
        factory.setProviders(providerList);
        factory.create();
//        logger.info("CXF REST is starting at:" + Manager.Server_Domain);
//        System.out.println("rest ws is published");
    }
}
