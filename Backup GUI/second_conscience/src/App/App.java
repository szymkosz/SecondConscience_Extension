package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by emilyhowing on 3/27/18.
 */
public class App {
    // Maximum negative sentiment score
    float sentimentThreshold = (float) 0.20;
    // Minimum LUIS match
    float minLUISMatchScore = (float) 0.85;

    void ButtonWork(){
        JFrame f = new JFrame("Button");
        JButton b = new JButton("Submit");
        b.setBounds(100,100,140,40);
        JLabel label = new JLabel();
        label.setText("Enter message:");
        label.setBounds(10,10,100,100);
        JLabel label1 = new JLabel();
        label1.setBounds(10,110,200,100);
        JTextField textfield = new JTextField();
        textfield.setBounds(110,50,130,30);
        f.add(label1);
        f.add(textfield);
        f.add(label);
        f.add(b);
        f.setSize(300,300);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Capture the message and analyze sentiment
                String message = textfield.getText();
                float score = conscience(message);

                // If the score falls below the negative sentiment threshold
                if (score < sentimentThreshold) {

                    // Have to catch the exceptions thrown eventually...
                    try {
                        String LUISResponse = LuisGetRequest.sendGet(message);
                        String topIntent = HttpUrlConnect.findTopIntent(LUISResponse);
                        float newScore = HttpUrlConnect.findScore(LUISResponse);
                        if (newScore > minLUISMatchScore && !topIntent.equals("None")) {
                            label1.setText("bullying detected");
                        }
                    } catch (Exception ex) {
                        System.out.println("LuisGetRequest failed");
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        App a = new App();
        a.ButtonWork();
    }

    public static float conscience(String text) {
        HttpUrlConnect obj = new HttpUrlConnect();
        Documents docs = new Documents();
        docs.add("1", "en", text);
        float score = 1;
        try {
            String ret = GetSentiment.GetSentiment(docs);
            ret = GetSentiment.prettify(ret);
            score = HttpUrlConnect.findScore(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return score;
        //System.out.println("score: " + score);
    }
}
