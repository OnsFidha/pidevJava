package edu.esprit.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class BadWordsApi {
    public static String filterBadWords(String text) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, text);

        Request request = new Request.Builder()
                .url("https://api.apilayer.com/bad_words?censor_character=*")
                .addHeader("apikey", "bVEylMhimuHW7Yu87yPdA5szAFE64xLU")
                .method("POST", body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                // Utiliser JsonParser pour analyser la réponse JSON
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                // Extraire le contenu de la clé "censored_content"
                String censoredContent = jsonResponse.get("censored_content").getAsString();
                return censoredContent;
            } else {
                throw new IOException("Erreur lors de la demande API : " + response.code());
            }
        }
    }
}
