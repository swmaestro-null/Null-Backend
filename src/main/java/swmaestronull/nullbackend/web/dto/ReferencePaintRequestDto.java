package swmaestronull.nullbackend.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class ReferencePaintRequestDto {

    @NotNull
    private String email;

    @NotNull
    private String referenceFileName;

    @NotNull
    private String sketchFileName;

}
