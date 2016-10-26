package kenneth.jf.siaapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Kenneth on 26/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class eventDetailsObject {

    @JsonProperty("id")
    private Long id;

}
