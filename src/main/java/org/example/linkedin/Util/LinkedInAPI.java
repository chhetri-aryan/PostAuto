package org.example.linkedin.Util;

import javafx.scene.control.Alert;
import org.example.linkedin.Model.Session;
import org.example.linkedin.ODM.MongoODM;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.example.linkedin.Util.CreateAlert.createAlert;

public class LinkedInAPI {

    private static final String USER_INFO = "https://api.linkedin.com/v2/userinfo";
    private static final String API_ENDPOINT = "https://api.linkedin.com/v2/ugcPosts";
    private static final String REGISTER_UPLOAD_URL = "https://api.linkedin.com/v2/assets?action=registerUpload";
    private static String ACCESS_TOKEN = "AQU7Nk1qWzEpC2w_K_uOZeZd4t_MOuIu2d-KevjWneTCdrOqUI14vSjX44NHXKqL36T3JbAAPYwODV9opMcp5Rx6NjdqYLongUSuMvjygzBneAixKCj-_dX7Gx8E5VoecXTBHKx6syZz9bjolH0vGIODHj_4Okt3eB5uqcKRY0eH8nZ3ybQ5wRBtSWvMrKUsM2zbcdqvwH4SlM1JwQOgBBOpmqMxyJBheQ-aQnOSEPH4sMXE6VxlSGecGhKeW4ZFwlwGJg3sxLP8CoQPr00rj0IXaTsr9ZRTiFrNxS76jkeTbgtSSWkYxr2k04bNRIaiY3P_StRHtaUPtESfVFB3QHv1rnB8hg"; // Replace with your access token
    private static String PERSON_URN = "urn:li:person:nP2dKVeG-B"; // Replace with your person URN
    static String x = "urn:li:person:nP2dKVeG-B";

    public static void main(String[] args) {
//        fetchURN(ACCESS_TOKEN);
//        createTextPost("Hello LinkefdsfdIn from Java!","This is aryan right here");
    }

    private static void fetchURN() {

        try {

            URL url = new URL(USER_INFO);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
            int responseCode = connection.getResponseCode();

            if (responseCode != 200 ) {
                createAlert("Error fetching user details, Please make sure the Access key is Correct", Alert.AlertType.INFORMATION);
                return;
            }
            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = in.readLine().split(":")[1].split(",")[0].replace("\"","");
            PERSON_URN = "urn:li:person:" + inputLine;
//            System.out.println(PERSON_URN);


        }
      catch(Exception e) {
          System.out.println(e.getMessage());
          createAlert("Error Occurred, Please try again later", Alert.AlertType.INFORMATION);

      }
    }


    public static boolean createTextPost(String title, String content, String accessKey) {
        fetchURN();
        ACCESS_TOKEN = accessKey;
        try {
            String postData = "{"
                    + "\"author\": \"" + PERSON_URN + "\","
                    + "\"lifecycleState\": \"PUBLISHED\","
                    + "\"specificContent\": {"
                    + "\"com.linkedin.ugc.ShareContent\": {"
                    + "\"shareCommentary\": {"
                    + "\"text\": \"" + title + "\\n" + content + "\""
                    + "},"
                    + "\"shareMediaCategory\": \"NONE\""
                    + "}"
                    + "},"
                    + "\"visibility\": {"
                    + "\"com.linkedin.ugc.MemberNetworkVisibility\": \"PUBLIC\""
                    + "}"
                    + "}";

            URL url = new URL(API_ENDPOINT);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");

            con.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-li-format", "json");

            con.setDoOutput(true);

            try (DataOutputStream outStream = new DataOutputStream(con.getOutputStream())) {
                outStream.writeBytes(postData);
                outStream.flush();
            }

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            if (responseCode == 201) {
                MongoODM odm = new MongoODM();
                odm.updateURL(ACCESS_TOKEN);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println(response);
            }

            con.disconnect();

        } catch (Exception e) {
            createAlert("Error while posting ❌ .", Alert.AlertType.INFORMATION);
            System.out.println(e.getMessage());
            return false;
        }

        return true;

    }

}
