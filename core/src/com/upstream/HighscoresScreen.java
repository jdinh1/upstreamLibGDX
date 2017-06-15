

package com.upstream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.input;


public class HighscoresScreen extends ScreenAdapter {
	final String highScoreUrl = "http://tuturucoding.com/upstream/highscores.json";
	UPstream game;
	OrthographicCamera guiCam;
	Rectangle backBounds;
	Vector3 touchPoint;
	List<Integer> tempHighScores;
	List<String> tempNames;
	List<String> tempModes;
	String [] highScores;
	private HttpURLConnection conn;
	private URL url_load;
	float xOffset = 0;
	GlyphLayout glyphLayout = new GlyphLayout();


	public HighscoresScreen (final UPstream game) throws IOException {
		try {
			url_load = new URL(highScoreUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.game = game;
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		backBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector3();
		highScores = new String[20];
		tempHighScores = new ArrayList<Integer>();
		tempNames = new ArrayList<String>();
		tempModes = new ArrayList<String>();
		//sendRequest();
        String url="http://tuturucoding.com/upstream/highscores.json";
        final PlayerXMLparser players = new PlayerXMLparser();

        URL url_load = new URL(url);
        final HttpURLConnection urlConnection = (HttpURLConnection) url_load.openConnection();

        AsyncTask task = new AsyncTask<Void>() {
            @Override
            public Void call() throws Exception {
                doInBackground();
                return null;
            }

            protected void doInBackground() {

                try {

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //readStream(in);

					// Convert inputstream object to string and pass into GDX's xml reader
					String result = getStringFromInputStream(in);
//					XmlReader reader = new XmlReader();
//					XmlReader.Element root = reader.parse(result);
//					Array<XmlReader.Element> players = root.getChildrenByName("player");

					Gdx.app.debug("Json info", " - "+ result);

					JsonValue json = new JsonReader().parse(result);
					JsonValue players = json.get("players").get("player");
					JsonValue.JsonIterator playersIt = players.iterator();
					while(playersIt.hasNext()) {
						JsonValue data = playersIt.next();

						String name = data.getString("name");
						int score = data.getInt("score");
						int mode = data.getInt("mode");

						String tempMode = "";
						switch(mode) {
							case 1:
								tempMode = "E";
								break;
							case 2:
								tempMode = "M";
								break;
							case 3:
								tempMode = "H";
								break;
						}
						tempNames.add(name);
						tempHighScores.add(score);
						tempModes.add(tempMode);

						//.app.debug("Json info2", " - "+ test);
					}
					int score = json.getInt("score");
					int mode = json.getInt("mode");


					// Grabbing scores data and pushing scores data into corresponding arrays
//					for (XmlReader.Element child : players)
//					{
//						String name = child.getChildByName("name").getText();
//						String score = child.getChildByName("score").getText();
//						String mode = child.getChildByName("mode").getText();
//
//
//						// Game mode string -> int
//						int gameModeXML = 1;
//						if (mode == "easy") {
//							gameModeXML = 1;
//						} else if (mode == "medium") {
//							gameModeXML = 2;
//						} else if (mode == "hard") {
//							gameModeXML = 3;
//						}
//
//						tempNames.add(name);
//						tempHighScores.add(score);
//						tempModes.add(gameModeXML);
//
//						// debug in logcat
//						Gdx.app.debug("High Score info", " - " + score);
//					}

					//players.parseXml(in);

                    Label.LabelStyle style = new Label.LabelStyle();
                   // style.background = ...; // Set the drawable you want to use
                   //highScores=players.toString();

                    glyphLayout.setText(Assets.font, (CharSequence) players.toString());


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }

        };
        try {
            task.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

		for (int i = 0; i < tempHighScores.size(); i++) {
			highScores[i] = i + 1 + ". " + tempNames.get(i) + " -" + tempHighScores.get(i);
			glyphLayout.setText(Assets.font, tempHighScores.get(i) + " ("+ tempModes.get(i) +")");

			xOffset = Math.max(glyphLayout.width, xOffset);
		}

        //InputStream webscoresInputStream = new URL(url).openConnection().getInputStream();
       // PlayerXMLparser players = new PlayerXMLparser();
        //players.parseXml(webscoresInputStream);
        //glyphLayout.setText(Assets.font, (CharSequence) players);

		xOffset = 160 - xOffset / 2 + Assets.font.getSpaceWidth() / 2;

	}


	public void update () {
		if (input.justTouched()) {
			guiCam.unproject(touchPoint.set(input.getX(), input.getY(), 0));

			if (backBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}

	public void draw () {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		guiCam.update();

		game.batcher.setProjectionMatrix(guiCam.combined);
		game.batcher.disableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundRegion, 0, 0, 320, 480);
		game.batcher.end();

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.highScoresRegion, 10, 360 - 16, 300, 33);

		float y = 230;
		for (int i = 4; i >= 0; i--) {
			Assets.font.draw(game.batcher, highScores[i], xOffset / 2, y);
			y += Assets.font.getLineHeight();
		}

		game.batcher.draw(Assets.arrow, 0, 0, 64, 64);
		game.batcher.end();
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

	@Override
	public void render (float delta) {
		update();
		draw();
	}

	public void sendRequest() {
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

					// Read JSON as string and parse it
					InputStream in = new BufferedInputStream(conn.getInputStream());

					String result = getStringFromInputStream(in);

					// debug in logcat
					Gdx.app.debug("Json info", " - "+ result);

					JsonValue json = new JsonReader().parse(result);

//					String error = json.getString("err");
//					String msg = json.getString("msg");



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
}
