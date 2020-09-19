package scriptloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import scriptloader.model.ScriptPath;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ScriptLoader {

    private static final ScriptLoader instance = new ScriptLoader();

    synchronized public static ScriptLoader getInstance() {
        return instance;
    }

    public ScriptPath loadScript() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(URLDecoder.decode(
                    new File(this.getClass().getClassLoader().getResource("script.json").getFile()).getAbsolutePath(),
                    java.nio.charset.StandardCharsets.UTF_8.toString()));
            return objectMapper.readValue(file, ScriptPath.class);

        } catch (IOException e) {
            log.error("Error while parsing script", e);
        }
        return null;
    }

}
