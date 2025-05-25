import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

    int n;

    @Override
    public void start(Stage primaryStage) {
        // VBox as the root
        VBox root = new VBox();
        root.setPrefSize(599, 750);

        // AnchorPane inside VBox
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(599, 719);

        // GridPane (3x3 as in FXML)
        GridPane gridPane_n = new GridPane();
        gridPane_n.setPrefSize(530, 530);
        gridPane_n.setLayoutX(27);
        gridPane_n.setLayoutY(148);

        // Column constraints (3 columns)
        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setMinWidth(10.0);
            cc.setPrefWidth(100.0);
            cc.setHgrow(Priority.SOMETIMES);
            gridPane_n.getColumnConstraints().add(cc);
        }

        // Row constraints (3 rows)
        for (int i = 0; i < 3; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setMinHeight(10.0);
            rc.setPrefHeight(30.0);
            rc.setVgrow(Priority.SOMETIMES);
            gridPane_n.getRowConstraints().add(rc);
        }

        // Button: Genetics
        Button button_Genetics = new Button("Genetics");
        button_Genetics.setLayoutX(385);
        button_Genetics.setLayoutY(49);
        button_Genetics.setOnAction(e -> {
            char[][] result = runGenetics(n);
            setGrid(n, result);

        });

        // Button: BackTracking
        Button button_BackTracking = new Button("BackTracking");
        button_BackTracking.setLayoutX(471);
        button_BackTracking.setLayoutY(49);
        button_BackTracking.setOnAction(e -> {
            char[][] result = runBackTracking(n);
            setGrid(n, result);
        });

        // TextField
        TextField textField_n = new TextField();
        textField_n.setLayoutX(198);
        textField_n.setLayoutY(49);

        // Label
        Label label_NoQ = new Label("Number of Queens :");
        label_NoQ.setLayoutX(42);
        label_NoQ.setLayoutY(51);
        label_NoQ.setFont(new Font(14));

        // Add all nodes to anchorPane
        anchorPane.getChildren().addAll(
                gridPane_n,
                button_Genetics,
                button_BackTracking,
                textField_n,
                label_NoQ
        );

        // Add anchorPane to VBox root
        root.getChildren().add(anchorPane);

        // Set scene and show stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("N Queens");
        primaryStage.show();
    }

    public char[][] runGenetics(int n){
        char[][] result = new char[n][n];
        return result;
    }

    public char[][] runBackTracking(int n){
        char[][] result = new char[n][n];
        return result;
    }

    public void setGrid(int n, char[][] result){

    }

    public static void main(String[] args) {
        launch(args);
    }
}
