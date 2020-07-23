package espe.edu.ec.carga_horaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CargaHorariaApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
    return application.sources(CargaHorariaApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(CargaHorariaApplication.class, args);
                System.out.println("Sistema carga horaria");
	        System.out.println("Done!");
	}

}
