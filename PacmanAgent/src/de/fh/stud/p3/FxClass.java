package de.fh.stud.p3;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import de.fh.kiServer.agents.Agent;


public class FxClass extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Font f = new Font("Arial", 15);
        Label inhalt = new Label("Select one of the follwoing methods:");
        inhalt.setFont(new Font("Arial", 18));
        inhalt.setPadding(new Insets(5));

        Button tiefenSuche = new Button("1. TiefenSuche");
        tiefenSuche.setMinWidth(250);
        tiefenSuche.setFont(f);

        Button breitenSuche = new Button("2. BreitenSuche");
        breitenSuche.setFont(f);
        breitenSuche.setMinWidth(250);

        Button ucs = new Button("3. Uniform-Cost Search (UCS)");
        ucs.setFont(f);
        ucs.setMinWidth(250);

        Button greedy = new Button("4. Greedy Search");
        greedy.setFont(f);
        greedy.setMinWidth(250);

        Button astern = new Button("5. A*");
        astern.setFont(f);
        astern.setMinWidth(250);

        GridPane gp = new GridPane();
        gp.setVgap(5.0);

        gp.add(inhalt, 0 ,0 );
        gp.add(tiefenSuche, 0 ,2 );
        gp.add(breitenSuche, 0 ,3 );
        gp.add(ucs, 0 ,4 );
        gp.add(greedy, 0 ,5 );
        gp.add(astern, 0 ,6 );

        GridPane.setHalignment(tiefenSuche, HPos.CENTER);
        GridPane.setHalignment(breitenSuche, HPos.CENTER);
        GridPane.setHalignment(ucs, HPos.CENTER);
        GridPane.setHalignment(greedy, HPos.CENTER);
        GridPane.setHalignment(astern, HPos.CENTER);

        Scene szene = new Scene(gp, 300,200);
        primaryStage.setTitle("Pac-Man search Tool");
        primaryStage.setScene(szene);
        primaryStage.show();

    }
}
