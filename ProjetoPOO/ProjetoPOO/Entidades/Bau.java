package Entidades;

public class Bau extends Entidades{
    //ATRIBUTOS
    String item;
    

    //CONSTRUTOR
    public Bau(String item) {
        this.item = item;
    }

    //MÉTODOS
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

}