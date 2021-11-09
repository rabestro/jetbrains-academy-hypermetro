package metro;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Stage5 {
    public static void main(String[] args) {
        final var appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        final var application = appContext.getBean("application", Application.class);
        application.run(args[0]);
    }

}