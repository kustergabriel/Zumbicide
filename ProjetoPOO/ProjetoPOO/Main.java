public class Main {
    public static void main(String[] args) {
        
    /*
    Mapa mapa = new Mapa();
    InterfaceJogo mainFrameApp = new InterfaceJogo();
    mainFrameApp.setVisible(true);
    Entidades player = new Jogador(); */

    Mapa mapa = new Mapa();
    InterfaceJogo interfaceJogo = new InterfaceJogo(); 
    Controlador controlador = new Controlador(mapa, interfaceJogo);
    interfaceJogo.setVisible(true);

    }
}