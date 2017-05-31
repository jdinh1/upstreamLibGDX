package com.upstream;

/**
 * Created by captnemo on 5/29/2017.
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class PlayerXMLparser {
    public ArrayList parseXml(InputStream in) {
        //Create a empty link of players initially
        ArrayList<Player> players = new ArrayList<Player>();
        try {
            //Create default handler instance
            PlayerParserHandler handler = new PlayerParserHandler();

            //Create parser from factory
            XMLReader parser = XMLReaderFactory.createXMLReader();

            //Register handler with parser
            parser.setContentHandler(handler);

            //Create an input source from the XML input stream
            InputSource source = new InputSource(in);

            //parse the document
            parser.parse(source);

            //populate the parsed players list in above created empty list; You can return from here also.
            players = handler.getPlayers();

        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

        }
        return players;
    }
}