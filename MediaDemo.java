import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebView;

public class MediaDemo extends Application {
    private static final String MEDIA_URL =
        "https://liveexample.pearsoncmg.com/common/sample.mp4";

    // private static final String[] MEDIA_LIST ={
    // "https://youtu.be/1OQEtAAa66k?list=PLVoYTKrMlQ4bpu-QQmNs3CHT4UqQ0ZCsB",
    // "https://youtu.be/dsK8SsfqdHE?list=PLoxa3g8sc0jPnClVaqXjpPknAmtxjL62M",
    // "https://youtu.be/PDSkFeMVNFs?list=PLoxa3g8sc0jPnClVaqXjpPknAmtxjL62M"};

    private static final String[] MEDIA_NAME = {
        "Kimi no na wa",
        "Black Clover",
        "Zen Zen Zense"
    };

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        Media media = new Media(MEDIA_URL);
        Media[] nyMedia = {
            new Media("https://home.usn.no/lonnesta/kurs/OBJ2100/Leksjoner/Leksjon01/Grafisk.mp4"),
            new Media("https://home.usn.no/lonnesta/kurs/OBJ2100/Leksjoner/Leksjon01/introduksjon.mp4"),
            new Media("https://www.youtube.com/embed/n4o-i71Jz4E")
        };
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        Button playButton = new Button(">");
        playButton.setOnAction(e -> {
            if (playButton.getText().equals(">")) {
                mediaPlayer.play();
                playButton.setText("||");
            } else {
                mediaPlayer.pause();
                playButton.setText(">");
            }
        });

        Button rewindButton = new Button("<<");
        rewindButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));

        Slider slVolume = new Slider();
        slVolume.setPrefWidth(150);
        slVolume.setMaxWidth(Region.USE_PREF_SIZE);
        slVolume.setMinWidth(30);
        slVolume.setValue(50);
        mediaPlayer.volumeProperty().bind(
            slVolume.valueProperty().divide(100));

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(playButton, rewindButton,
            new Label("Volume"), slVolume);



        ListView <String> lv = new ListView <>
            (FXCollections.observableArrayList(MEDIA_NAME));
        lv.setPrefSize(150, 200);
        lv.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        BorderPane pane = new BorderPane();
        pane.setLeft(new ScrollPane(lv));
        pane.setCenter(mediaView);
        pane.setBottom(hBox);

        lv.getSelectionModel().selectedItemProperty().addListener(
            ov -> {
                pane.getChildren().clear();
            
                mediaPlayer.pause();
                for (Integer i: lv.getSelectionModel().getSelectedIndices()) {
                    MediaPlayer nyMediaPlayer = new MediaPlayer(nyMedia[i]);
                    MediaView nyMediaView = new MediaView(nyMediaPlayer);
                    pane.getChildren().add(nyMediaView);
                    pane.setLeft(new ScrollPane(lv));
                    Button nyPlayButton = new Button(">");
                    playButton.setOnAction(e -> {
                        if (nyPlayButton.getText().equals(">")) {
                            nyMediaPlayer.play();
                            nyPlayButton.setText("||");
                        } else {
                            nyMediaPlayer.pause();
                            nyPlayButton.setText(">");
                        }
                });
                HBox hotBox = new HBox(10);
                hotBox.setAlignment(Pos.CENTER);
                hotBox.getChildren().addAll(playButton, rewindButton,
                new Label("Volume"), slVolume);
                pane.setBottom(hotBox);
            }

        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 650, 500);
        primaryStage.setTitle("MediaDemo"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage    
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}