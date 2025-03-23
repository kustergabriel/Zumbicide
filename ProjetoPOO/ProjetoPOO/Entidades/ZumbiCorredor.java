package Entidades;
public class ZumbiCorredor extends Zumbi {
    // Zumbi Corredor tem 2 de vida

    public ZumbiCorredor () {
        super(3,2);
    }

    @Override
    public String toString() {
        return "Zumbi Corredor";
    }
}
