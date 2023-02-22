/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

/**
 *
 * @author hazemsobiha
 */
public class SetApp extends Application {

    MenuBar bar;
    Menu file;
    Menu edit;
    Menu help;
    BorderPane root;
    MenuItem newItem1;
    MenuItem newItem2;
    MenuItem newItem3;
    MenuItem newItem4;

    MenuItem editItem1;
    MenuItem editItem2;
    MenuItem editItem3;
    MenuItem editItem4;
    MenuItem editItem5;
    MenuItem editItem6;

    MenuItem helpItem1;

    TextArea area;
    String content;
    String selectedText;

    @Override
    public void init() throws Exception {
        super.init();
        bar = new MenuBar();
        file = new Menu("File");
        edit = new Menu("Edit");
        help = new Menu("Help");
        newItem1 = new MenuItem("New");
        newItem1.setAccelerator(KeyCombination.keyCombination("alt+n"));
        newItem2 = new MenuItem("Open");
        newItem3 = new MenuItem("Save");
        newItem4 = new MenuItem("Exit");

        editItem1 = new MenuItem("Undo");
        editItem6 = new MenuItem("Cut");
        editItem2 = new MenuItem("Copy");
        editItem3 = new MenuItem("Paste");
        editItem4 = new MenuItem("Delete");
        editItem5 = new MenuItem("Select All");

        helpItem1 = new MenuItem("About Notepad");

        file.getItems().addAll(newItem1, newItem2, newItem3, newItem4);
        SeparatorMenuItem sep = new SeparatorMenuItem();
        file.getItems().add(3, sep);

        edit.getItems().addAll(editItem1, editItem6, editItem2, editItem3, editItem4, editItem5);
        SeparatorMenuItem sep2 = new SeparatorMenuItem();
        SeparatorMenuItem sep3 = new SeparatorMenuItem();
        edit.getItems().add(1, sep2);
        edit.getItems().add(6, sep3);

        help.getItems().addAll(helpItem1);

        bar.getMenus().addAll(file, edit, help);

        area = new TextArea();

        root = new BorderPane();
        root.setTop(bar);
        root.setCenter(area);
    }

    @Override
    public void start(Stage primaryStage) {
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("About Nodepad");
        dialog.setContentText("Hayah Hazem Created this notepad.");
        ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        helpItem1.setOnAction(e -> {
            dialog.showAndWait();
        });

        FileChooser fil_chooser = new FileChooser();
//        Label label = new Label("no files selected");

        EventHandler<ActionEvent> openFileEvent
                = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                // get the file selected
                File file = fil_chooser.showOpenDialog(primaryStage);
                content = "";
                if (file != null) {
                    try {
                        Scanner myReader = new Scanner(file);
                        while (myReader.hasNextLine()) {
                            content += myReader.nextLine();
                        }
                        myReader.close();
                        primaryStage.setTitle(file.getName());
                        area.setText(content);
                        

                    } catch (FileNotFoundException e1) {
                        System.out.println("An error occurred.");
                        e1.printStackTrace();
                    }
                }
            }
        };

        EventHandler<ActionEvent> saveFileEvent
                = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {

                // get the file selected
                File file = fil_chooser.showSaveDialog(primaryStage);
                content = area.getText();
                if (file != null) {
                    try {
                        FileWriter myWriter = new FileWriter(file.getAbsolutePath());
                        myWriter.write(content);
                        myWriter.close();
                        primaryStage.setTitle(file.getName());
                    } catch (IOException e2) {
                        System.out.println("An error occurred.");
                        e2.printStackTrace();
                    }
                }
            }
        };

        EventHandler<ActionEvent> newFileEvent
                = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                area.clear();
                content = "";
            }
        };

        EventHandler<ActionEvent> exitEvent
                = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Platform.exit();
            }
        };
        EventHandler<ActionEvent> selectAllEvent
                = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                area.selectAll();
            }
        };
        EventHandler<ActionEvent> copyTextEvent
                = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                selectedText = area.getSelectedText();
            }
        };
        EventHandler<ActionEvent> cutTextEvent
                = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                selectedText = area.getSelectedText();
                area.replaceSelection("");
            }
        };
        EventHandler<ActionEvent> deleteTextEvent
                = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                area.replaceSelection("");
            }
        };
        EventHandler<ActionEvent> pasteTextEvent
                = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                area.replaceSelection(selectedText);
            }
        };
        EventHandler<ActionEvent> undoFileEvent
                = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                area.undo();
            }
        };

        newItem1.setOnAction(newFileEvent);
        newItem2.setOnAction(openFileEvent);
        newItem3.setOnAction(saveFileEvent);
        newItem4.setOnAction(exitEvent);
        editItem5.setOnAction(selectAllEvent);
        editItem6.setOnAction(cutTextEvent);
        editItem2.setOnAction(copyTextEvent);
        editItem3.setOnAction(pasteTextEvent);
        editItem4.setOnAction(deleteTextEvent);
        editItem1.setOnAction(undoFileEvent);
        Scene scene = new Scene(root, 500, 350);

        primaryStage.setTitle("Untitled-JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
