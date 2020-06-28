package utn.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BroadcastCall {

    @JsonProperty
    Float duration;

    @JsonProperty
    String phoneNumberDestiny;

    @JsonProperty
    String originPhone;
}
