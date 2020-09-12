import fr.arakne.utils.maps.serializer.DefaultMapDataSerializer;
import fr.arakne.utils.maps.serializer.MapDataSerializer;

public class HelloWorld {

    public static void main( String[] args )
    {
        DefaultMapDataSerializer serializer = new DefaultMapDataSerializer();
        serializer.enableCache();
        System.out.println( "Hello World!" );
    }

}
