package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import sample.controllers.ManageDvdController;

public class Dvd {


    public String id, name, genre, realase, director, price, countity;

    public Button update;
    public Button delete;

    public Dvd(String id, String name, String genre, String relase, String director, String price, String countity, Button update, Button delete) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.realase = relase;
        this.director = director;
        this.price = price;
        this.countity = countity;
        this.update = update;
        this.delete = delete;

        update.setOnAction(e->{
            ObservableList<Dvd> dvds = ManageDvdController.table_info_two.getSelectionModel().getSelectedItems();
            for(Dvd dvd: dvds){
                if(dvd.getUpdate() == update){
                    Main.printWriter.println("updateDvd,"+
                            dvd.getId()+","+
                            dvd.getName()+","+
                            dvd.getGenre()+","+
                            dvd.getRealase()+","+
                            dvd.getDirector()+","+
                            dvd.getPrice()+","+
                            dvd.getCountity()
                    );
                    Main.printWriter.flush();
                }
            }
        });

        delete.setOnAction(e->{
            ObservableList<Dvd> dvds = ManageDvdController.table_info_two.getSelectionModel().getSelectedItems();
            for(Dvd dvd: dvds){
                if(dvd.getDelete() == delete){
                    Main.printWriter.println("deleteDvd,"+ dvd.getId());
                    Main.printWriter.flush();
                    dvds.forEach(ManageDvdController.data_table::remove);
                }
            }
        });

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRealase() {
        return realase;
    }

    public void setRealase(String relase) {
        this.realase = relase;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCountity() {
        return countity;
    }

    public void setCountity(String countity) {
        this.countity = countity;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
