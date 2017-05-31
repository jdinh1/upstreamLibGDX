

package com.upstream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.async.AsyncTask;


import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.Response;




public class HighscoresScreen extends ScreenAdapter {
	UPstream game;
	OrthographicCamera guiCam;
	Rectangle backBounds;
	Vector3 touchPoint;
	String[] highScores;
    String[] names;
    int[] modes;

	float xOffset = 0;
	GlyphLayout glyphLayout = new GlyphLayout();



	public HighscoresScreen (final UPstream game) throws IOException {
		this.game = game;

		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		backBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector3();
		highScores = new String[20];
        names = new String[20];
        modes = new int[20];
        String url="http://chilembwe.com/upstream/highscore.xml";
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
                    players.parseXml(in);
                    Label.LabelStyle style = new Label.LabelStyle();
                   // style.background = ...; // Set the drawable you want to use
                    highScores[1]=players.toString();
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

        //InputStream webscoresInputStream = new URL(url).openConnection().getInputStream();
       // PlayerXMLparser players = new PlayerXMLparser();
        //players.parseXml(webscoresInputStream);
        //glyphLayout.setText(Assets.font, (CharSequence) players);
		for (int i = 0; i < 5; i++) {
			highScores[i] = i + 1 + ". " + Settings.highscores[i];
			glyphLayout.setText(Assets.font, highScores[i]);

			xOffset = Math.max(glyphLayout.width, xOffset);
		}
		xOffset = 160 - xOffset / 2 + Assets.font.getSpaceWidth() / 2;

	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

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
			Assets.font.draw(game.batcher, highScores[i], xOffset, y);
			y += Assets.font.getLineHeight();
		}

		game.batcher.draw(Assets.arrow, 0, 0, 64, 64);
		game.batcher.end();
	}

	@Override
	public void render (float delta) {
		update();
		draw();
	}
}
