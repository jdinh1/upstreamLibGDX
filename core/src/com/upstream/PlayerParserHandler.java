package com.upstream;

/**
 * Created by captnemo on 5/29/2017.
 */
import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PlayerParserHandler extends DefaultHandler
{
    //This is the list which shall be populated while parsing the XML.
    private ArrayList playerList = new ArrayList();

    //As we read any XML element we will push that in this stack
    private Stack elementStack = new Stack();

    //As we complete one player block in XML, we will push the Player instance in playerList
    private Stack objectStack = new Stack();

    public void startDocument() throws SAXException
    {
        //System.out.println("start of the document   : ");
    }

    public void endDocument() throws SAXException
    {
        //System.out.println("end of the document document     : ");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        //Push it in element stack
        this.elementStack.push(qName);

        //If this is start of 'player' element then prepare a new Player instance and push it in object stack
        if ("player".equals(qName))
        {
            //New Player instance
            Player player = new Player();

            //Set all required attributes in any XML element here itself
            if(attributes != null && attributes.getLength() == 1)
            {
                player.setName(new String(attributes.getValue(0)));
            }
            this.objectStack.push(player);
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        //Remove last added  element
        this.elementStack.pop();

        //Player instance has been constructed so pop it from object stack and push in playerList
        if ("player".equals(qName))
        {
            Player object = (Player) this.objectStack.pop();
            this.playerList.add(object);
        }
    }

    /**
     * This will be called everytime parser encounter a value node
     * */
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();

        if (value.length() == 0) {
            return; // ignore white space
        }

        //handle the value based on to which element it belongs
        if ("name".equals(currentElement())) {
            Player player = (Player) this.objectStack.peek();
            player.setName(value);
        } else if ("score".equals(currentElement())) {
            Player player = (Player) this.objectStack.peek();
            player.setScore(Integer.parseInt(value));
        } else if ("mode".equals(currentElement())) {
            Player player = (Player) this.objectStack.peek();
            player.setMode(Integer.parseInt(value));
        }
    }

    /**
     * Utility method for getting the current element in processing
     * */
    private String currentElement()
    {
        return (String) this.elementStack.peek();
    }

    //Accessor for playerList object
    public ArrayList getPlayers()
    {
        return playerList;
    }
}