package editor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Editor extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private List<Text> listOfText = new ArrayList<>();

    private class KeyEventHandler implements EventHandler<KeyEvent> {
        int textX;
        int textY;

        boolean startOfLine = true;
        private static final int STARTING_FONT_SIZE = 20;
        private int STARTING_TEXT_POSITION_X = 250;
        private int STARTING_TEXT_POSITION_Y = 250;

        /**
         * The Text to display on the screen.
         */
        private Text displayText;// = new Text(STARTING_TEXT_POSITION_X, STARTING_TEXT_POSITION_Y, "");
        private int fontSize = STARTING_FONT_SIZE;
        Group rootCopy;
        private String fontName = "Verdana";
        KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
            textX = 0;
            textY = 0;

            // Initialize some empty text and add it to root so that it will be displayed.
            displayText = new Text(textX, textY, "");
            //listOfText.add(displayText);
            displayText.setTextOrigin(VPos.TOP);
            displayText.setFont(Font.font(fontName, fontSize));

            // All new Nodes need to be added to the root in order to be displayed.
            rootCopy = root;
            //root.getChildren().addAll(listOfText);
            //rootCopy.getChildren().addAll(listOfText);
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                String characterTyped = keyEvent.getCharacter();
                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                    // Ignore control keys, which have non-zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows.
                    //displayText.setText(characterTyped);
                    //STARTING_TEXT_POSITION_X++;
                    displayText = new Text(textX, textY, characterTyped);
                    //listOfText.add(displayText);
                    //System.out.println(listOfText.size());
                   // System.out.println(listOfText);
                    keyEvent.consume();
                    if(characterTyped.equals("\r")){
                        alignText("enter");
                    }
                    else{
                        alignText("typed");
                    }

                }

                //alignText("typed");
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.UP) {
                    //fontSize += 5;
                    /*for(Text t : listOfText)
                        t.setFont(Font.font(fontName, fontSize));*/
                    //displayText.setFont(Font.font(fontName, fontSize));
                    alignText("up");
                } else if (code == KeyCode.DOWN) {
                    /*fontSize = Math.max(0, fontSize - 5);
                    for(Text t : listOfText)
                        t.setFont(Font.font(fontName, fontSize));*/
                    //displayText.setFont(Font.font(fontName, fontSize));
                    alignText("down");
                }
                else if (code == KeyCode.BACK_SPACE){
                    if(listOfText.size() > 0) {
                        //Node txt = rootCopy.getChildren().get(rootCopy.getChildren().size() - 1);
                        //System.out.println(txt + " node txt");
                        ///rootCopy.getChildren().remove(rootCopy.getChildren().size() - 1);
                        //System.out.println(rootCopy.getChildren().get(rootCopy.getChildren().size() - 1) + " last");
                        alignText("backspace");
                    }
                }
            }
        }

        private void alignText(String keyboard) {
            if(keyboard.equals("typed")){
                int textHeight = (int)Math.ceil(displayText.getLayoutBounds().getHeight());
                int textWidth = (int)Math.ceil(displayText.getLayoutBounds().getWidth());
                System.out.println("ceil "+(int)Math.ceil(displayText.getLayoutBounds().getWidth()));
                System.out.println("no ceil "+displayText.getLayoutBounds().getWidth());
                //int linenumber = textY;

                if(!listOfText.isEmpty()) {
                    if (startOfLine){
                        textX = 0;
                        startOfLine = false;
                    }
                    else {
                        textX += (int) listOfText.get(listOfText.size() - 1).getLayoutBounds().getWidth();
                    }
                }
                //else textX = 0;
                displayText.setTextOrigin(VPos.TOP);
                displayText.setX(textX);
                displayText.setY(textY);
                displayText.setFont(Font.font(fontName, fontSize));
                listOfText.add(displayText);
                System.out.println(textX); //
                displayText.toFront();
                rootCopy.getChildren().add(displayText);
            }

            else if(keyboard.equals("backspace")){
                Text lastChar = listOfText.get(listOfText.size()-1);
                //Text lastChar = (Text)rootCopy.getChildren().get(rootCopy.getChildren().size()-1);
                //int textHeight = (int)lastChar.getLayoutBounds().getHeight();
                //int textWidth = (int)Math.ceil(displayText.getLayoutBounds().getWidth());
                //int textWidth = (int)displayText.getLayoutBounds().getWidth();
                //double textTop = textY + 10;//not needed
                //double textLeft = textX -= textWidth;
                listOfText.remove(listOfText.size()-1);
                if(!listOfText.isEmpty()){
                    if(lastChar.getY()==0){
                        int textWidth = (int) listOfText.get(listOfText.size() - 1).getX();
                        textX = textWidth;
                    }
                    else{
                        int textWidth = (int) listOfText.get(listOfText.size()-1).getX();
                        textX = textWidth;
                        textY -= lastChar.getY()-listOfText.get(listOfText.size()-1).getY();
                    }
                }
               /* if(textY > 0 && textX == 0 ){
                    int textHeight = (int)listOfText.get(0).getLayoutBounds().getHeight();
                    textY -= textHeight;
                    textX = (int)listOfText.get(listOfText.size()-1).getX();
                }
                else {
                    if (listOfText.size() >= 2) {
                        int textWidth = (int) listOfText.get(listOfText.size() - 1).getX();
                        textX = textWidth;
                        int textHeight = (int)listOfText.get(listOfText.size() - 1).getLayoutBounds().getHeight();
                        textY -= textHeight;
                    } else {
                        textX = 0;
                    }
                }*/
                /*System.out.println("size before remove "+rootCopy.getChildren().size());
                System.out.println(rootCopy.getChildren());*/
                //listOfText.remove(listOfText.size()-1);
                rootCopy.getChildren().remove(lastChar);
                /*System.out.println("size after remove "+rootCopy.getChildren().size());
                System.out.println(rootCopy.getChildren());
                System.out.println(rootCopy.getChildren().size());*/
            }

            else if(keyboard.equals("up")){
                fontSize += 4;
               /* for(Text t : listOfText){
                    t.setFont(Font.font(fontName, fontSize));
                    //t.setX(t.getX()+20);
                    if (t.getX() != 0){
                        t.setX(t.getX() + 25);
                    }
                }*/
               for(int i = 0; i < listOfText.size(); i++){
                   Text t = listOfText.get(i);
                   t.setFont(Font.font(fontName, fontSize));
                   if (t.getX() != 0){
                       t.setX(listOfText.get(i-1).getX() + listOfText.get(i-1).getLayoutBounds().getWidth());
                   }
                   /*if (t.getY() != 0){
                       t.setY(t.getY() + t.getLayoutBounds().getHeight());
                   }*/
               }
            }
            else if(keyboard.equals("down")){
                if(fontSize > 4){
                    fontSize -= 4;
                    listOfText.get(0).setFont(Font.font(fontName, fontSize));
                    for(int i = 1; i < listOfText.size(); i++){
                        // ovjde staviti uslov zbog kraja reda
                        Text t = listOfText.get(i);
                        if (t.getX() != 0) {
                            t.setX(listOfText.get(i - 1).getX() + listOfText.get(i - 1).getLayoutBounds().getWidth());
                        }
                        t.setFont(Font.font(fontName, fontSize));
                    }
                }
            }
            else if(keyboard.equals("enter")){
                textX = 0;
                startOfLine = true;
                textY += (int) new Text("a").getLayoutBounds().getHeight();
            }
            // Figure out the size of the current text.
            /*double textHeight = displayText.getLayoutBounds().getHeight();
            double textWidth = displayText.getLayoutBounds().getWidth();
*/
            // Calculate the position so that the text will be centered on the screen.
           /* double textTop =textY+10;
            double textLeft = textX += textWidth;*/ // textCenterX - textWidth / 2;

            // Re-position the text.
           /* displayText.setX(textLeft);
            displayText.setY(textTop);
            listOfText.add(displayText);
            // Make sure the text appears in front of any other objects you might add.
            displayText.toFront();
            rootCopy.getChildren().add(displayText);*/


        }
    }
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);
        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        primaryStage.setTitle("Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}