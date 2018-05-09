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
                    System.out.println(listOfText.size());
                   // System.out.println(listOfText);
                    keyEvent.consume();
                    alignText("typed");

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
                    if(rootCopy.getChildren().size() > 0) {
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
                double textHeight = displayText.getLayoutBounds().getHeight();
                double textWidth = displayText.getLayoutBounds().getWidth();
                double textTop =textY;
                textX += textWidth;
                displayText.setTextOrigin(VPos.TOP);
                displayText.setX(textX);
                displayText.setY(textTop);
                listOfText.add(displayText);
                displayText.toFront();
                rootCopy.getChildren().add(displayText);
            }

            else if(keyboard.equals("backspace")){
                Text lastChar = (Text)rootCopy.getChildren().get(rootCopy.getChildren().size()-1);
                int textHeight = (int)lastChar.getLayoutBounds().getHeight();
                int textWidth = (int)lastChar.getLayoutBounds().getWidth();
                //double textTop = textY + 10;//not needed
                //double textLeft = textX -= textWidth;
                textX -= textWidth;
                /*System.out.println("size before remove "+rootCopy.getChildren().size());
                System.out.println(rootCopy.getChildren());*/
                rootCopy.getChildren().remove(rootCopy.getChildren().size()-1);
                /*System.out.println("size after remove "+rootCopy.getChildren().size());
                System.out.println(rootCopy.getChildren());
                System.out.println(rootCopy.getChildren().size());*/
            }

            else if(keyboard.equals("up")){
                fontSize += 4;
                for(Text t : listOfText){
                    t.setFont(Font.font(fontName, fontSize));
                }
            }
            else if(keyboard.equals("down")){
                if(fontSize > 4){
                    fontSize -= 4;
                    for(Text t : listOfText){
                        t.setFont(Font.font(fontName, fontSize));
                    }
                }
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