package Entidades;
public class ZumbiGigante extends Zumbi {
    // Zumbi Gigante tem 3 de vida

    public ZumbiGigante () {
        super(7,3);
    }
    
    @Override
    public String toString() {
        return "Zumbi Gigante";
    }
}
