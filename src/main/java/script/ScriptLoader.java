package script;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import script.model.ScriptPath;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ScriptLoader {

    private static final ScriptLoader instance = new ScriptLoader();

    synchronized public static ScriptLoader getInstance() {
        return instance;
    }

    public ScriptPath loadScript() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(this.getClass().getClassLoader().getResource("script.json").getFile());
            return objectMapper.readValue(file, ScriptPath.class);

        } catch (IOException e) {
            log.error("Error while parsing script", e);
        }
        return null;
    }

}
