package com.upstream;
/**
 * Created by Jonathan on 5/31/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.TimerTask;
import static com.upstream.Settings.mode;

public class Scoring {
    final static String sessionStartUrl = "http://tuturucoding.com/upstream/scoring_test.php";
    final static String sharedSecretKey = "upstream_gxUIg8BfcXYk9b2Qikmd";       // need obfuscation before shipping
    AeSimpleSHA1 sha1Object;
    String sessionToken = "";
    int currentScore = 0;
    String secretHashValue ="";
    String tokenHashValue ="";
    URL url_load;
    HttpURLConnection conn;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    CookieManager cookieManager = new java.net.CookieManager();
    private java.util.Timer autoModeTimer = new java.util.Timer();
    boolean passToken, gameover, isHighScore;
    public Scoring() {
        // Storing cookies or else connection will reset
        CookieHandler.setDefault(cookieManager);

        passToken = false;
        gameover = false;
        isHighScore = false;
        sha1Object = new AeSimpleSHA1();

        // initiate new URL object
        try {
            url_load = new URL(sessionStartUrl);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Grab new session when game starts
        try {
            secretHashValue = sha1Object.SHA1(sharedSecretKey);
            this.autoModeTimer.schedule(new TimerTask() {
                @Override public void run() {
                    sendRequest(secretHashValue,mode,passToken,gameover,isHighScore);
                }}, 0);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    };

    public int updateScore (int gamemode){
        // bool passToken must be true every time we update score
        gameover = false;
        passToken = true;
        this.autoModeTimer.schedule(new TimerTask() {
            @Override public void run() {
                sendRequest(secretHashValue,mode,passToken,gameover,isHighScore);
            }}, 0);
        return currentScore;

    }

    public void sendHighScoreToServer (int gamemode){
        // bool passToken must be true every time we update score
        gameover = true;
        passToken = true;
        isHighScore = true;
        this.autoModeTimer.schedule(new TimerTask() {
            @Override public void run() {
                sendRequest(secretHashValue,mode,passToken,gameover,isHighScore);
            }}, 0);
    }

    public void gameOver(int gamemode) {
        gameover = true;
        passToken = true;
        String newToken = null;
        try {
            newToken = sha1Object.SHA1(sessionToken + sharedSecretKey);

            sendRequest(newToken,gamemode,passToken,gameover,isHighScore);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String secretHash, final int gamemode, final boolean passToken, final boolean gameover, final boolean isHighScore) {
        // Request sent here
        AsyncTask task = new AsyncTask<Void>() {
            @Override
            public Void call() throws Exception {
                doInBackground();
                return null;
            }

            protected void doInBackground() {

                try {
                    conn = (HttpURLConnection) url_load.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    // building POST request string
                    String data = URLEncoder.encode("extra", "UTF-8") + "=" + URLEncoder.encode(secretHashValue, "UTF-8");
                    data += "&" + URLEncoder.encode("mode", "UTF-8") + "=" + URLEncoder.encode(gamemode+"", "UTF-8");
                    if (passToken) {
                        data += "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(tokenHashValue, "UTF-8");
                    }
                    if (gameover) {
                        data += "&" + URLEncoder.encode("gameover", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
                    }
                    if (isHighScore) {
                        data += "&" + URLEncoder.encode("highscore", "UTF-8") + "=" + URLEncoder.encode(currentScore+"", "UTF-8");
                    }

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    wr.close();

                    // Read JSON as string and parse it
                    InputStream in = new BufferedInputStream(conn.getInputStream());

                    String result = getStringFromInputStream(in);

                    // debug in logcat
                    Gdx.app.debug("Json info", " - "+ result);

                    JsonValue json = new JsonReader().parse(result);
                    sessionToken = json.getString("token");
                    String error = json.getString("err");
                    String msg = json.getString("msg");
                    currentScore = json.getInt("score");
                    // Set hash of token+secretkey using new token from server
                    try {
                        tokenHashValue = sha1Object.SHA1(sessionToken + sharedSecretKey);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    conn.disconnect();
                }
            }

        };
        try {
            task.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();

    }

}
