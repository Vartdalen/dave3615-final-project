package no.oslomet.gatewayservice;

import no.oslomet.gatewayservice.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public StorageService storageService() {
        return new StorageService() {
            @Override
            public void init() {

            }

            @Override
            public void store(MultipartFile file) {

            }

            @Override
            public Stream<Path> loadAll() {
                return null;
            }

            @Override
            public Path load(String filename) {
                return null;
            }

            @Override
            public Resource loadAsResource(String filename) {
                return null;
            }

            @Override
            public void deleteAll() {

            }
        };
    }

    @Bean
    CommandLineRunner init (StorageService storageService){
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
