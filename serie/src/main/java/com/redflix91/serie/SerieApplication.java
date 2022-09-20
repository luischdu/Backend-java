package com.redflix91.serie;

import com.redflix91.serie.vista.Ventana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.redflix91.serie")
public class SerieApplication {

	public static void main(String[] args) {
                new Ventana().setVisible(true);
        }
        
        public static void runSpringServer(String[] args) {
            /*
            ConfigurableApplicationContext ctx = new SpringApplicationBuilder(Application.class)
                    .headless(false).run(args);
            */
            SpringApplication.run(SerieApplication.class, args);
        }        

}