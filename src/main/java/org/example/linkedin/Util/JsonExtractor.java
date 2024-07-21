package org.example.linkedin.Util;

import org.json.JSONObject;


public class JsonExtractor {
    public static void main(String[] args) {
        String s1 = "{\"result\":\"\\\"Hello World: A Journey into the Realm of Coding and Beyond\\\"\",\"status\":true,\"server_code\":1}\n";
        String s2 = "{\"result\":\"Title: \\\"Hello World - A New Beginning in the Digital Realm\\\"\\n\\nLinkedIn Post Content:\\nGreetings, fellow professionals! Today marks a new beginning as we welcome ourselves into the digital realm. The phrase 'hello' has been used countless times across various platforms and languages throughout history; it is not only an expression of recognition but also serves as our introduction to something or someone that piques interest and curiosity. This post aims at redefining what those simple words\",\"status\":true,\"server_code\":1}";

        System.out.println(extractTitle(s1));
        System.out.println(extractSubject(s2));

    }

    public static String extractTitle(String res) {
        JSONObject jsonObject = new JSONObject(res);
        return jsonObject.getString("result").replace("\"","");
    }

    public static String extractSubject(String res) {
        JSONObject jsonObject = new JSONObject(res);
        return jsonObject.getString("result").replace("\"","");
    }

}
