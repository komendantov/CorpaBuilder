package ru.komendantov.corpabuilder.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CorpusDocumentUtils {

    public HashMap<Integer, String> doReplacesInText(HashMap<String, String> userReplaces, List<String> textList) {


        HashMap<Integer, String> textWithReplaces = new HashMap<>();
        for (int i = 0; i < textList.size(); i++) {
            Set<String> keySet = userReplaces.keySet();
            for (String key : keySet) {
                String word = textList.get(i);
                if (word.contains(key)) {
                    textWithReplaces.put(i, word.replace(key, userReplaces.get(key)));
                } else {
                    textWithReplaces.put(i, word);
                }
            }
        }
        return textWithReplaces;
    }

    public JSONArray revertReplaces(String text, List<String> originalTextArray) {
        JSONArray responseJson = new JSONArray(text);
        JSONArray resultJson = new JSONArray();
        int c = responseJson.length();
        for (int i = 0; i < responseJson.length() && i < originalTextArray.size(); i++) {
            JSONObject responseElement = responseJson.getJSONObject(i);
            // JSONObject analysis = responseElement.getJSONObject("analysis");
//            String analysisText = responseElement.getString("text");
//            replaces.get(i);
//

//            Set<String> keySet = replaces.keySet();
//            for (String key : keySet) {
//                String value = replaces.get(key);
//                analysisText = analysisText.replace(value, key);
//            }
            responseElement.put("text", originalTextArray.get(i));
            resultJson.put(responseElement);
        }
        return responseJson;
    }
}