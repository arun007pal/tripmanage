package com.example.tripmanage;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FirebaseUidFinder {

//    public static void main(String[] args) throws IOException {
//        String email = "example@example.com";
//        String uid = getUidByEmail(email);
//        System.out.println("UID associated with email " + email + ": " + uid);
//    }

    public static String getUidByEmail(String email) {
        // Construct the URL for the Firebase Authentication REST API
        try {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:lookup?key=AIzaSyBiv97CHVlLf24JaVK2vNsLbfEzKBC5JBo";

        // Create the JSON payload for the request
        String requestBody = "{\"email\":\"" + email + "\"}";

        // Send POST request to the Firebase Authentication REST API
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

        // Read the response
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        // Parse the response JSON to extract the UID
        String jsonResponse = response.toString();
        int startIndex = jsonResponse.indexOf("\"localId\":\"") + "\"localId\":\"".length();
        int endIndex = jsonResponse.indexOf("\"", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
        } catch (Exception e) {
            return "";
//            throw new RuntimeException(e);
        }
    }

    public interface Callback {
        void onComplete(String uid);
    }

    // Method to recursively search for the string within child nodes and return the UID
    public static void searchForString(DatabaseReference nodeRef, String searchString, Callback callback) {
        // Add a ValueEventListener to the node reference
        final int[] ii = {0};
        nodeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Initialize a variable to store the UID
                String uid = null;

                // Iterate through the child nodes
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    // Check if the value of the current child node is a string
                    if (childSnapshot.getValue() instanceof String) {
                        String value = (String) childSnapshot.getValue();
                        // Check if the string matches the desired search string
                        if (value.equals(searchString)) {
                            // If the string is found, set the uid and return
                            uid = dataSnapshot.getKey();
//                            Log.d("H","No");
                            callback.onComplete(uid);
                            return;
                        }
                    } else {
                        ii[0]++;

                        for (DataSnapshot childSnapshott : childSnapshot.getChildren()) {
                            // Check if the value of the current child node is a string
                            if (childSnapshott.getValue() instanceof String) {
                                String value = (String) childSnapshott.getValue();
                                // Check if the string matches the desired search string
                                if (value.equals(searchString)) {
                                    // If the string is found, set the uid and return
                                    uid = childSnapshot.getKey();
//                                    Log.d("H", "No");
                                    callback.onComplete(uid);
                                    return;
                                }
                            }
                        }
                        // If the value is not a string, recursively search within its children
//                        searchForString(childSnapshot.getRef(), searchString, new Callback() {
//                            @Override
//                            public void onComplete(String childUid) {
//                                if (childUid != null) {
//                                    ii[0]++;
//                                    Log.d("H","NoN");
//                                    // If the string is found in the child's subtree, return the UID
//                                    callback.onComplete(childUid);
//                                    return;
//                                }
//                            }
//                        });
                    }
                }
                // Invoke the callback with the UID
//                if(ii[0] > 1) {
//                    Log.d("H","NoR");
                    callback.onComplete(uid);
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Error: " + databaseError.getMessage());
                // Invoke the callback with null if an error occurs
                callback.onComplete(null);
            }
        });
    }
}
