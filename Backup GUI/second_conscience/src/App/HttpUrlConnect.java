package App;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUrlConnect {

    private final String USER_AGENT = "Mozilla/5.0";

    // HTTP GET request
    public String sendGet(String query) throws Exception {

        String url = "https://westus.api.cognitive.microsoft.com/luis/v2.0/apps/84b1f33f-ec76-4539-a01e-1d0dada1f10a?subscription-key=20d2344d1a06408caa9465dc4bd4d380&staging=true&spellCheck=true&bing-spell-check-subscription-key=4946a79341fa49268fb57c4a01aa1166&verbose=true&timezoneOffset=-360&q=";

        // Remove the spaces and replace them with '%20'
        String[] splitArray = query.split("\\s+");
        String newQuery = String.join("%20", splitArray);

        // Append the query to our bot url
        url += newQuery;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        // add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String ret = response.toString();

        return ret;
    }

    public static float findScore(String input) {
        int scoreIndexStart = input.indexOf("score") + 8;
        int idx = scoreIndexStart+2;
        while (Character.isDigit(input.charAt(idx))) {
            idx++;
        }
        int scoreIndexEnd = idx;
        String num = input.substring(scoreIndexStart, scoreIndexEnd); //get num
        num = num.replaceAll("\\s",""); //remove whitespace
        float numFinal = Float.parseFloat(num); //cast to float
        return numFinal;
    }

    public static String findTopIntent(String input) {
        int intentIdxStart = input.indexOf("intent") + 10;
        int intentIdxEnd = input.indexOf("\"", intentIdxStart);
        return input.substring(intentIdxStart, intentIdxEnd);
    }
}
