package metro;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Stage4 {
    public static void main(String[] args) {
        final var appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        final var application = appContext.getBean("application", Application.class);

        application.run();
    }

}
