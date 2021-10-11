package swmaestronull.nullbackend.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket api(){
        TypeResolver typeResolver = new TypeResolver();
        List<ResponseMessage> commonResponse = setCommonResponse();
        Set<String> contentType = Set.of(MediaType.APPLICATION_JSON_VALUE);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API v1")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v1/**")).build()
                .directModelSubstitute(OffsetDateTime.class, String.class)
                .consumes(contentType)
                .produces(contentType)
                .globalResponseMessage(RequestMethod.GET, commonResponse)
                .globalResponseMessage(RequestMethod.POST, commonResponse)
                .globalResponseMessage(RequestMethod.PUT, commonResponse)
                .globalResponseMessage(RequestMethod.PATCH, commonResponse)
                .globalResponseMessage(RequestMethod.DELETE, commonResponse)
                .additionalModels(typeResolver.resolve(ResponseEntity.class));
    }

    private List<ResponseMessage> setCommonResponse() {
        List<ResponseMessage> list = new ArrayList<>();
        list.add(new ResponseMessageBuilder().code(404).message("Not Found").build());
        list.add(new ResponseMessageBuilder().code(500).message("Internal Error").build());
        return list;
    }
}
