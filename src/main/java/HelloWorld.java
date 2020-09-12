import loader.MapsLoader;
import loader.dto.MapsDto;
import lombok.extern.log4j.Log4j2;
import model.dofus.RetroDofusMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@Log4j2
public class HelloWorld {

    public static void main( String[] args )
    {
        log.info("STart");
        MapsDto mapsDto = null;
        try {
            mapsDto = MapsLoader.loadMapsData();
        } catch (URISyntaxException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }
        log.info("Starting init");
        mapsDto.getMaps().stream()
                .map(RetroDofusMap::new)
                .collect(Collectors.toList());
        log.info("Init done");
    }

}
