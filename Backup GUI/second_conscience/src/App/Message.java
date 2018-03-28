package App;

/**
 * Created by emilyhowing on 3/27/18.
 */
public class Message {

    public static float conscience(String text) {
        String first = text;
        HttpUrlConnect obj = new HttpUrlConnect();
        float score = 0;
        Documents docs = new Documents();
        docs.add("1", "en", text);
        try {
            //Log.i("Tag", "1");
            String ret = GetSentiment.GetSentiment(docs);
            //Log.i("Tag", "2");
            ret = GetSentiment.prettify(ret);
            //Log.i("Tag", "3");
            score = HttpUrlConnect.findScore(ret);
            //Log.i("Tag", "did it");
        } catch (Exception e) {
            e.printStackTrace();
        }

//	       try {
//	           score = HttpURLConnect.findScore( obj.sendGet(text));
//	       } catch (Exception e) {
//	           e.printStackTrace();
//	       }


        //float score = HttpURLConnect.findScore(first);
        //System.out.println("score: " + score);

//            if (score < .20) {
//                //positive for cyberbullying
//                return score;
//            }
//            //negative for cyberbullying
//            else {
//                return 1;
//                //do nothing
//            }

        //}
        return (float) .99;

    }
}

