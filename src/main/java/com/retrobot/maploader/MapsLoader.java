package com.retrobot.maploader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.retrobot.maploader.dto.MapsDto;

import java.io.*;
import java.net.URISyntaxException;

public class MapsLoader {

    public static MapsDto loadMapsData() throws URISyntaxException, IOException {
        File mapsFile = new File(MapsLoader.class.getClassLoader().getResource("maps.xml").toURI());
        XmlMapper xmlMapper = new XmlMapper();
        String xml = inputStreamToString(new FileInputStream(mapsFile));
        MapsDto mapsData = xmlMapper.readValue(xml, MapsDto.class);
        return mapsData;
    }

    private static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

}
