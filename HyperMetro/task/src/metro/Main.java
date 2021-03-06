package metro;

import metro.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        final var appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        final var application = appContext.getBean("application", Application.class);
        application.start(args[0]);
    }

}
