package com.example.final_game;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class GameController {
    @FXML
    ImageView stick;
    Boolean isSpaceKeypressed=false;
    @FXML
    ImageView character;
    boolean mousePressed;
    @FXML
    Rectangle tower1;
    @FXML
    Rectangle tower2;
    @FXML
    Rectangle tower3;
    @FXML
    ImageView cherry;
    int current_tower=1;
    boolean canElongate=true;

    public void elongateRod(MouseEvent event) throws IOException,InterruptedException{
        mousePressed=true;
            Thread t1 = new Thread(()->{
                while(mousePressed){
                    double New_Height=stick.getFitHeight()+5;
                    stick.setFitHeight(New_Height);
                    stick.setLayoutY(stick.getLayoutY()-5);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }

            });
            t1.start();
        }


    public void stopelongation(MouseEvent event) throws InterruptedException, IOException {
        mousePressed = false;
        Thread t2=new Thread(()->{
            try {
                moveCharacter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                checkifontower();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(current_tower==3){
                resetScene();
                current_tower=1;
            }
        });
        t2.start();
    }

    public void moveCharacter() throws IOException, InterruptedException {
        stick.setRotate(90);
        stick.setLayoutX(stick.getLayoutX()+stick.getFitHeight()/2);
        stick.setLayoutY(stick.getLayoutY()+stick.getFitHeight()/2);
        double Distance=stick.getFitHeight();
        double Covered=0;
        while(Covered<Distance){
            if(isSpaceKeypressed){
                character.setRotate(180);
            }
            character.setLayoutX(character.getLayoutX() + 5);
            Covered+=5;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //character.setLayoutX(character.getLayoutX() + stick.getFitHeight());
        stick.setFitHeight(0.01);
        stick.setRotate(0);
        stick.setLayoutX(character.getLayoutX() + 5);
        stick.setLayoutY(character.getLayoutY() + character.getFitHeight());
        current_tower++;
    }

    public void resetScene(){
        tower2.setOpacity(0);
        tower1.setOpacity(0);
        double move=tower3.getLayoutX();
        tower3.setLayoutX(tower1.getLayoutX());
        tower1.setLayoutX(move);
        character.setLayoutX(tower3.getLayoutX());
        Rectangle temp=tower1;
        tower1=tower3;
        tower3=temp;
        Random random = new Random();
        tower2.setWidth(random.nextInt(41) + 28);
        tower3.setWidth(random.nextInt(41) + 28);
        tower3.setOpacity(1);
        tower2.setOpacity(1);
        stick.setLayoutX(character.getLayoutX() + 5);
        stick.setLayoutY(character.getLayoutY() + character.getFitHeight());
        int c=random.nextInt(3);
        if(c==1){
            cherry.setLayoutY(tower1.getLayoutY());
            cherry.setLayoutX(tower1.getLayoutX()+tower1.getWidth()+random.nextInt(-(int)tower1.getLayoutX()+(int)tower2.getLayoutX()));
            cherry.setFitHeight(20);
            cherry.setFitWidth(20);
        }
        if(c==2){
            cherry.setLayoutY(tower2.getLayoutY());
            cherry.setLayoutX(tower2.getLayoutX()+tower2.getWidth()+random.nextInt((int)tower3.getLayoutX()-(int)tower2.getLayoutX()));
            cherry.setFitHeight(20);
            cherry.setFitWidth(20);
        }

    }
    public void checkifontower() throws IOException {
        if(character.getLayoutX()<getTower().getLayoutX()||
                character.getLayoutX()>getTower().getLayoutX()+getTower().getWidth()){
            Platform.runLater(()->{
                Stage stage= (Stage) tower1.getScene().getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameOver.fxml")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            });
        }
    }

    public Rectangle getTower(){
        if(current_tower==1){
            return tower1;
        }
        if(current_tower==2){
            return tower2;
        }
        return tower3;
    }
    public void invert(KeyEvent event){
        Thread t3 = new Thread(()->{
            System.out.println("yes");
            if(event.getCode()==KeyCode.E){
                isSpaceKeypressed=true;
            }
        });
        t3.start();
    }
    public void noInvert(KeyEvent event){
        Thread t4= new Thread(()->{
            System.out.println("no");
            if(event.getCode()==KeyCode.E){
                isSpaceKeypressed=false;
            }
        });
        t4.start();
    }
}
