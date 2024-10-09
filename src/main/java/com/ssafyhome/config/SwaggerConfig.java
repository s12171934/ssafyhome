package com.ssafyhome.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "SSAFY HOME",
        description = "ssafy home 아파트 매매 정보 시스템"
    ),
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class SwaggerConfig {

  @Bean
  public OpenAPI loginmOpenAPI() {
    OpenAPI openAPI = new OpenAPI();
    openAPI.addTagsItem(new Tag().name("Authentication").description("인증 및 인가"));

    PathItem loginPath = new PathItem();
    Operation loginOperation = new Operation()
        .addTagsItem("Authentication")
        .summary("로그인을 통한 jwt token 발급")
        .description("Authorization에 access token, Cookie에 refresh token을 삽입하여 반환")
        .requestBody(new RequestBody()
            .content(new Content()
                .addMediaType("application/x-www-form-urlencoded",
                    new MediaType()
                        .schema(new Schema<>()
                            .type("object")
                            .addProperties("username", new Schema<>().type("string"))
                            .addProperties("password", new Schema<>().type("string"))
                        )
                )
            )
        )
        .responses(new ApiResponses()
            .addApiResponse("200", new ApiResponse()
                .description("로그인 성공")
                .addHeaderObject("Set-Cookie", new Header()
                    .description("Refresh token")
                    .schema(new Schema<>().type("string"))
                )
                .addHeaderObject("Authorization", new Header()
                    .description("Access token")
                    .schema(new Schema<>().type("string"))
                )
                .content(new Content()
                    .addMediaType("application/json",
                        new MediaType()
                            .schema(new Schema<>()
                                .type("object")
                                .addProperties("message", new Schema<>().type("string").example("로그인 성공"))
                            )
                    )
                )
            )
        );

    loginPath.setPost(loginOperation);
    openAPI.path("/auth/login", loginPath);

    return openAPI;
  }
}
