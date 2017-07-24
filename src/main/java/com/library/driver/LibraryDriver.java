package com.library.driver;

import com.library.configuration.PersistenceJPAConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


/**
 * Created by Pavlo-Anton on 23-Jul-17.
 */
public class LibraryDriver {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceJPAConfig.class);
        Application application = context.getBean(Application.class);
        application.run();
    }
}
