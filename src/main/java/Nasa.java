import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.net.URL;


public class Nasa {
    public static ObjectMapper mapper = new ObjectMapper();

    static void nasaJpg(String host) {
        var httpGet = new HttpGet(host);
        try (var fileWriter = new FileWriter("C2022E3ZTFMountEtna.dat"); var httpClient = HttpClients.createDefault()) {
            var response = httpClient.execute(httpGet);
            var nasaInfo = mapper.readValue(response.getEntity().getContent(), NasaInfo.class);
            try (var in = new BufferedInputStream(new URL(nasaInfo.getHdurl()).openStream()); var out = new FileOutputStream("C2022E3ZTFMountEtna.jpg")) { //скачал картинку
                byte[] jpgNasa  = in.readAllBytes();// картинка
                out.write(jpgNasa);
                var httpGetInfo = new HttpGet(nasaInfo.getHdurl()); //основное тз
                var responseInfo = httpClient.execute(httpGetInfo);
                fileWriter.write(responseInfo.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        nasaJpg("https://api.nasa.gov/planetary/apod?api_key=dwc5BaQFcKG1fGihvB6aBvoAyXSZzjy13b2bm7pW");
    }
}
