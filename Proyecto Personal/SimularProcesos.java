import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.concurrent.Semaphore;


public class SimularProcesos {

    public static void main (String args[]){
        
        Graficos ventana = new Graficos();
        Sistema sistem = new Sistema(ventana);
        HiloProcesos hiloGenerar = new HiloProcesos(sistem, ventana);
        HiloEjecutar HiloEjecutar = new HiloEjecutar(sistem, ventana);

        ventana.vincularSistema(sistem);
        ventana.crearInterfaz();

        hiloGenerar.start();
        HiloEjecutar.start();

    }
}

 class Graficos{
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JScrollPane scrollPanePros;
    private javax.swing.JScrollPane scrollPaneEjecu;
    private javax.swing.JScrollPane scrollPaneHecho;
    private javax.swing.JScrollPane scrollPaneEx;
    private JPanel panelPros;
    private JPanel panelEjecu;
    private JPanel panelHecho;
    private JPanel panelEx;
    Sistema sistem;

    public void vincularSistema(Sistema sis){
        sistem = sis;
    }

    public void crearInterfaz(){
        initComponents();
        panels();


    }

     private void initComponents() {

        
        JFrame ventana = new JFrame("Simulador de procesos");

        ventana.setLocation(500,125);
        ventana.setSize(820, 650);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        

        panelPrincipal = new javax.swing.JPanel();
        panelPrincipal.setBackground(new java.awt.Color(63, 86, 100));
        panelPrincipal.setLayout(null);
        panelPrincipal.setPreferredSize(new Dimension(820, 500));


        JLabel etiProcesos = new JLabel("Lista de procesos: ");
        etiProcesos.setFont(new java.awt.Font("Segoe UI", 1, 18));
        etiProcesos.setForeground(new java.awt.Color(255, 255, 255));
        etiProcesos.setLocation(50,50);
        etiProcesos.setSize(200, 50);

        JLabel etiEjecu = new JLabel("En procesador: ");
        etiEjecu.setFont(new java.awt.Font("Segoe UI", 1, 18));
        etiEjecu.setForeground(new java.awt.Color(255, 255, 255));
        etiEjecu.setLocation(340,50);
        etiEjecu.setSize(200, 50);

        JLabel etiEx = new JLabel("Exclusiones: ");
        etiEx.setFont(new java.awt.Font("Segoe UI", 1, 18));
        etiEx.setForeground(new java.awt.Color(255, 255, 255));
        etiEx.setLocation(340,300);
        etiEx.setSize(200, 50);

        JLabel etiHecho = new JLabel("Finalizados: ");
        etiHecho.setFont(new java.awt.Font("Segoe UI", 1, 18));
        etiHecho.setForeground(new java.awt.Color(255, 255, 255));
        etiHecho.setLocation(620,50);
        etiHecho.setSize(200, 50);

        JLabel etiRam = new JLabel("Memoria: ");
        etiRam.setFont(new java.awt.Font("Segoe UI", 1, 14));
        etiRam.setForeground(new java.awt.Color(255, 255, 255));
        etiRam.setLocation(600,450);
        etiRam.setSize(200, 50);

        JLabel etiRam2 = new JLabel("MB");
        etiRam2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        etiRam2.setForeground(new java.awt.Color(255, 255, 255));
        etiRam2.setLocation(750,450);
        etiRam2.setSize(100, 50);

        JTextField txtRam = new JTextField("800");
        txtRam.setForeground(new java.awt.Color(0, 0, 0));
        txtRam.setBackground(new java.awt.Color(255, 255, 255));
        txtRam.setLocation(680, 460);
        txtRam.setSize(64, 31);

        txtRam.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                updateVariable();
            }

            private void updateVariable() {
                String text = txtRam.getText();
                try {
                    int ram = Integer.parseInt(text);
                    //setRAM
                    sistem.setRam(ram);
                } catch (NumberFormatException ex) {
                }
            }
        });

        JLabel etiTiempo = new JLabel("Tiempo ms: ");
        etiTiempo.setFont(new java.awt.Font("Segoe UI", 1, 14));
        etiTiempo.setForeground(new java.awt.Color(255, 255, 255));
        etiTiempo.setLocation(585,490);
        etiTiempo.setSize(200, 50);

        JTextField txtTiempo = new JTextField("15");
        txtTiempo.setForeground(new java.awt.Color(0, 0, 0));
        txtTiempo.setBackground(new java.awt.Color(255, 255, 255));
        txtTiempo.setLocation(680, 500);
        txtTiempo.setSize(64, 31);

        txtTiempo.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                updateVariable();
            }

            private void updateVariable() {
                String text = txtTiempo.getText();
                try {
                    int tiempo = Integer.parseInt(text);
                    //setTiempo
                    sistem.setTiempo(tiempo);
                } catch (NumberFormatException ex) {
                }
            }
        });

        JLabel etiGenerar = new JLabel("Generar cada: ");
        etiGenerar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        etiGenerar.setForeground(new java.awt.Color(255, 255, 255));
        etiGenerar.setLocation(580,530);
        etiGenerar.setSize(200, 50);

        JLabel etiGenerar2 = new JLabel("ms");
        etiGenerar2.setFont(new java.awt.Font("Segoe UI", 1, 14));
        etiGenerar2.setForeground(new java.awt.Color(255, 255, 255));
        etiGenerar2.setLocation(750,530);
        etiGenerar2.setSize(100, 50);

        JTextField txtGenerar = new JTextField("600");
        txtGenerar.setForeground(new java.awt.Color(0, 0, 0));
        txtGenerar.setBackground(new java.awt.Color(255, 255, 255));
        txtGenerar.setLocation(680, 540);
        txtGenerar.setSize(64, 31);

        txtGenerar.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                updateVariable();
            }

            private void updateVariable() {
                String text = txtGenerar.getText();
                try {
                    int generar = Integer.parseInt(text);
                    //setTiempo
                    sistem.setGenerar(generar);
                } catch (NumberFormatException ex) {
                }
            }
        });

        
        scrollPanePros = new JScrollPane();
        scrollPanePros.setBorder(null);

        scrollPaneEjecu = new JScrollPane();
        scrollPaneEjecu.setBorder(null);

        scrollPaneEx = new JScrollPane();
        scrollPaneEx.setBorder(null);

        scrollPaneHecho = new JScrollPane();
        scrollPaneHecho.setBorder(null);
        
        panelPrincipal.add(etiProcesos);
        panelPrincipal.add(etiEjecu);
        panelPrincipal.add(etiEx);
        panelPrincipal.add(etiHecho);
        panelPrincipal.add(etiRam);
        panelPrincipal.add(etiRam2);
        //panelPrincipal.add(botonGenerar);
        panelPrincipal.add(txtRam);
        panelPrincipal.add(etiTiempo);
        panelPrincipal.add(txtGenerar);
        panelPrincipal.add(etiGenerar);
        panelPrincipal.add(etiGenerar2);
        panelPrincipal.add(txtTiempo);
        scrollPanePros.setBounds(20, 120, 200, 450);
        panelPrincipal.add(scrollPanePros);
        scrollPaneEjecu.setBounds(300, 120, 200, 190);
        panelPrincipal.add(scrollPaneEjecu);
        scrollPaneEx.setBounds(300, 350, 200, 190);
        panelPrincipal.add(scrollPaneEx);
        scrollPaneHecho.setBounds(570, 120, 200, 300);
        panelPrincipal.add(scrollPaneHecho);


        
        ventana.add(panelPrincipal);
        ventana.setVisible(true);
     }

    
    int crecerProcesos = 70;
    int crecerEjecucion = 70;
    int crecerHechos = 70;
    int crecerEx = 70;
    int posYProcesos = 10;
    int posYEjecucion = 10;
    int posYHechos = 10;
    int posYEx = 10;
    
    
    private void panels() {
    panelPros = new JPanel();
    panelPros.setPreferredSize(new Dimension(180, crecerProcesos));
    panelPros.setLayout(null);
    panelPros.setBackground(new java.awt.Color(63, 86, 100));

    panelEjecu = new JPanel();
    panelEjecu.setPreferredSize(new Dimension(180, crecerEjecucion));
    panelEjecu.setLayout(null);
    panelEjecu.setBackground(new java.awt.Color(63, 86, 100));

    panelEx = new JPanel();
    panelEx.setPreferredSize(new Dimension(180, crecerEx));
    panelEx.setLayout(null);
    panelEx.setBackground(new java.awt.Color(63, 86, 100));

    panelHecho = new JPanel();
    panelHecho.setPreferredSize(new Dimension(180, crecerHechos));
    panelHecho.setLayout(null);
    panelHecho.setBackground(new java.awt.Color(63, 86, 100));
    
    //scrollPanePros.setBackground(new java.awt.Color(63, 86, 100));
    scrollPanePros.setViewportView(panelPros);

    //scrollPaneEjecu.setBackground(new java.awt.Color(63, 86, 100));
    scrollPaneEjecu.setViewportView(panelEjecu);

    //scrollPaneEx.setBackground(new java.awt.Color(63, 86, 100));
    scrollPaneEx.setViewportView(panelEx);

    //scrollPaneHecho.setBackground(new java.awt.Color(63, 86, 100));
    scrollPaneHecho.setViewportView(panelHecho);
        
    }

    private void crecerPanelPros(){
        panelPros.setPreferredSize(new Dimension(180, crecerProcesos));
    }
    private void crecerPanelEjecu(){
        panelEjecu.setPreferredSize(new Dimension(180, crecerEjecucion));
    }
    private void crecerPanelEx(){
        panelEx.setPreferredSize(new Dimension(180, crecerEx));
    }
    private void crecerPanelHecho(){
        panelHecho.setPreferredSize(new Dimension(180, crecerHechos));
    }

    public void agregarProceso(Proceso proceso){
        String nombre = proceso.getNombre();
            int id = proceso.getId();
            double memoria = proceso.getMemoria();
            int tiempo = proceso.getTiempo();
            int prioridad = proceso.getPrioridad();

            JLabel lblNombre = new JLabel(nombre);
            JLabel lblMemoria = new JLabel("Memoria: " + String.valueOf(memoria) + " MB");
            JLabel lblTiempo = new JLabel("Tiempo: " + String.valueOf(tiempo));
            JLabel lblPrioridad = new JLabel("Prioridad: " + String.valueOf(prioridad));
            JLabel lblSeparador = new JLabel("________________________");
            
            lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 16));
            lblMemoria.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblTiempo.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblPrioridad.setFont(new java.awt.Font("Segoe UI", 1, 16));
            
            lblNombre.setForeground(new java.awt.Color(255, 255, 255));
            lblMemoria.setForeground(new java.awt.Color(255, 255, 255));
            lblTiempo.setForeground(new java.awt.Color(255, 255, 255));
            lblPrioridad.setForeground(new java.awt.Color(150, 150, 150));
            lblSeparador.setForeground(new java.awt.Color(255, 255, 255));
            
            
            lblNombre.setLocation(35, posYProcesos);
            lblMemoria.setLocation(35,posYProcesos + 20);
            lblTiempo.setLocation(35,posYProcesos + 40);
            lblPrioridad.setLocation(35,posYProcesos + 60);
            lblSeparador.setLocation(35,posYProcesos + 80);
            
            posYProcesos = posYProcesos + 110;
            crecerProcesos = crecerProcesos + 110;
            
            lblNombre.setSize(100, 25);
            lblMemoria.setSize(160, 25);
            lblTiempo.setSize(100, 25);
            lblPrioridad.setSize(160, 25);
            lblSeparador.setSize(160, 25);

            panelPros.add(lblNombre);
            panelPros.add(lblMemoria);
            panelPros.add(lblTiempo);
            panelPros.add(lblPrioridad);
            panelPros.add(lblSeparador);
            
            crecerPanelPros();
            
            panelPros.revalidate();
            panelPros.repaint();
    }

    public void agregarEjecu(Proceso proceso){
        String nombre = proceso.getNombre();
            int id = proceso.getId();
            double memoria = proceso.getMemoria();
            int tiempo = proceso.getTiempo();
            int prioridad = proceso.getPrioridad();

            JLabel lblNombre = new JLabel(nombre);
            JLabel lblMemoria = new JLabel("Memoria: " + String.valueOf(memoria) + " MB");
            JLabel lblTiempo = new JLabel("Tiempo: " + String.valueOf(tiempo));
            JLabel lblPrioridad = new JLabel("Prioridad: " + String.valueOf(prioridad));
            JLabel lblSeparador = new JLabel("________________________");
            
            lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 16));
            lblMemoria.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblTiempo.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblPrioridad.setFont(new java.awt.Font("Segoe UI", 1, 16));
            
            lblNombre.setForeground(new java.awt.Color(255, 255, 255));
            lblMemoria.setForeground(new java.awt.Color(255, 255, 255));
            lblTiempo.setForeground(new java.awt.Color(255, 255, 255));
            lblPrioridad.setForeground(new java.awt.Color(150, 150, 150));
            lblSeparador.setForeground(new java.awt.Color(255, 255, 255));
            
            
            lblNombre.setLocation(35, posYEjecucion);
            lblMemoria.setLocation(35,posYEjecucion + 20);
            lblTiempo.setLocation(35,posYEjecucion + 40);
            lblPrioridad.setLocation(35,posYEjecucion + 60);
            lblSeparador.setLocation(35,posYEjecucion + 80);
            
            posYEjecucion = posYEjecucion + 110;
            crecerEjecucion = crecerEjecucion + 110;
            
            lblNombre.setSize(100, 25);
            lblMemoria.setSize(160, 25);
            lblTiempo.setSize(100, 25);
            lblPrioridad.setSize(160, 25);
            lblSeparador.setSize(160, 25);

            panelEjecu.add(lblNombre);
            panelEjecu.add(lblMemoria);
            panelEjecu.add(lblTiempo);
            panelEjecu.add(lblPrioridad);
            panelEjecu.add(lblSeparador);
            
            crecerPanelEjecu();
            
            panelEjecu.revalidate();
            panelEjecu.repaint();
    }

    public void agregarHecho(Proceso proceso){
        String nombre = proceso.getNombre();
            int id = proceso.getId();
            double memoria = proceso.getMemoria();
            int tiempo = proceso.getTiempo();
            int prioridad = proceso.getPrioridad();

            JLabel lblNombre = new JLabel(nombre);
            JLabel lblMemoria = new JLabel("Memoria: " + String.valueOf(memoria) + " MB");
            JLabel lblTiempo = new JLabel("Tiempo: " + String.valueOf(tiempo));
            JLabel lblPrioridad = new JLabel("Prioridad: " + String.valueOf(prioridad));
            JLabel lblSeparador = new JLabel("________________________");
            
            lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 16));
            lblMemoria.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblTiempo.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblPrioridad.setFont(new java.awt.Font("Segoe UI", 1, 16));
            
            lblNombre.setForeground(new java.awt.Color(255, 255, 255));
            lblMemoria.setForeground(new java.awt.Color(255, 255, 255));
            lblTiempo.setForeground(new java.awt.Color(255, 255, 255));
            lblPrioridad.setForeground(new java.awt.Color(150, 150, 150));
            lblSeparador.setForeground(new java.awt.Color(255, 255, 255));
            
            
            lblNombre.setLocation(35, posYHechos);
            lblMemoria.setLocation(35,posYHechos + 20);
            lblTiempo.setLocation(35,posYHechos + 40);
            lblPrioridad.setLocation(35,posYHechos + 60);
            lblSeparador.setLocation(35,posYHechos + 80);
            
            posYHechos = posYHechos + 110;
            crecerHechos = crecerHechos + 110;
            
            lblNombre.setSize(100, 25);
            lblMemoria.setSize(160, 25);
            lblTiempo.setSize(100, 25);
            lblPrioridad.setSize(160, 25);
            lblSeparador.setSize(160, 25);

            panelHecho.add(lblNombre);
            panelHecho.add(lblMemoria);
            panelHecho.add(lblTiempo);
            panelHecho.add(lblPrioridad);
            panelHecho.add(lblSeparador);
            
            crecerPanelHecho();
            
            panelHecho.revalidate();
            panelHecho.repaint();

            JScrollBar verticalScrollBar = scrollPaneHecho.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    public void agregarEx(Proceso proceso){
        String nombre = proceso.getNombre();
            int id = proceso.getId();
            double memoria = proceso.getMemoria();
            int tiempo = proceso.getTiempo();
            int prioridad = proceso.getPrioridad();

            JLabel lblNombre = new JLabel(nombre);
            JLabel lblMemoria = new JLabel("Memoria: " + String.valueOf(memoria) + " MB");
            JLabel lblTiempo = new JLabel("Tiempo: " + String.valueOf(tiempo));
            JLabel lblPrioridad = new JLabel("Prioridad: " + String.valueOf(prioridad));
            JLabel lblSeparador = new JLabel("________________________");
            
            lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 16));
            lblMemoria.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblTiempo.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblPrioridad.setFont(new java.awt.Font("Segoe UI", 1, 16));
            
            lblNombre.setForeground(new java.awt.Color(255, 255, 255));
            lblMemoria.setForeground(new java.awt.Color(255, 255, 255));
            lblTiempo.setForeground(new java.awt.Color(255, 255, 255));
            lblPrioridad.setForeground(new java.awt.Color(150, 150, 150));
            lblSeparador.setForeground(new java.awt.Color(255, 255, 255));
            
            
            lblNombre.setLocation(35, posYEx);
            lblMemoria.setLocation(35,posYEx + 20);
            lblTiempo.setLocation(35,posYEx + 40);
            lblPrioridad.setLocation(35,posYEx + 60);
            lblSeparador.setLocation(35,posYEx + 80);
            
            posYEx = posYEx + 110;
            crecerEx = crecerEx + 110;
            
            lblNombre.setSize(100, 25);
            lblMemoria.setSize(160, 25);
            lblTiempo.setSize(100, 25);
            lblPrioridad.setSize(160, 25);
            lblSeparador.setSize(160, 25);

            panelEx.add(lblNombre);
            panelEx.add(lblMemoria);
            panelEx.add(lblTiempo);
            panelEx.add(lblPrioridad);
            panelEx.add(lblSeparador);
            
            crecerPanelEx();
            
            panelEx.revalidate();
            panelEx.repaint();

            JScrollBar verticalScrollBar = scrollPaneEx.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    public void actualizarProcesos(List<Proceso> procesosOriginales){
        panelPros.removeAll();
            crecerProcesos = 70;
            posYProcesos = 10;

             List<Proceso> copiaProcesos = new ArrayList<>(procesosOriginales);

        for (Proceso proceso : copiaProcesos) {
            String nombre = proceso.getNombre();
            int id = proceso.getId();
            double memoria = proceso.getMemoria();
            int tiempo = proceso.getTiempo();
            int prioridad = proceso.getPrioridad();

            JLabel lblNombre = new JLabel(nombre);
            JLabel lblMemoria = new JLabel("Memoria: " + String.valueOf(memoria) + " MB");
            JLabel lblTiempo = new JLabel("Tiempo: " + String.valueOf(tiempo));
            JLabel lblPrioridad = new JLabel("Prioridad: " + String.valueOf(prioridad));
            JLabel lblSeparador = new JLabel("________________________");
            
            lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 16));
            lblMemoria.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblTiempo.setFont(new java.awt.Font("Segoe UI", 1, 14));
            lblPrioridad.setFont(new java.awt.Font("Segoe UI", 1, 16));
            
            lblNombre.setForeground(new java.awt.Color(255, 255, 255));
            lblMemoria.setForeground(new java.awt.Color(255, 255, 255));
            lblTiempo.setForeground(new java.awt.Color(255, 255, 255));
            lblPrioridad.setForeground(new java.awt.Color(150, 150, 150));
            lblSeparador.setForeground(new java.awt.Color(255, 255, 255));
            
            
            lblNombre.setLocation(35, posYProcesos);
            lblMemoria.setLocation(35,posYProcesos + 20);
            lblTiempo.setLocation(35,posYProcesos + 40);
            lblPrioridad.setLocation(35,posYProcesos + 60);
            lblSeparador.setLocation(35,posYProcesos + 80);
            
            posYProcesos = posYProcesos + 110;
            crecerProcesos = crecerProcesos + 110;
            
            lblNombre.setSize(100, 25);
            lblMemoria.setSize(160, 25);
            lblTiempo.setSize(100, 25);
            lblPrioridad.setSize(160, 25);
            lblSeparador.setSize(160, 25);

            panelPros.add(lblNombre);
            panelPros.add(lblMemoria);
            panelPros.add(lblTiempo);
            panelPros.add(lblPrioridad);
            panelPros.add(lblSeparador);
            
            crecerPanelPros();
            
            panelPros.revalidate();
            panelPros.repaint();
        }
    }

    public void borrarEjecu(){
        panelEjecu.removeAll();
            crecerEjecucion = 70;
            posYEjecucion = 10;
    }
}

class Proceso{
    
    private String nombre;
    private int id;
    private double memoria;
    private int tiempo;
    private int prioridad;

    public Proceso(int contador){
        Random random = new Random();
        //double numeroEnRango = rangoMinimo + (rangoMaximo - rangoMinimo) * random.nextDouble();
        //int numeroEnRango = random.nextInt(rangoMaximo - rangoMinimo + 1) + rangoMinimo;

        nombre = "Proceso " + contador;
        id = random.nextInt(10000 - 1 + 1) + 1;
        memoria = 10.00 + Math.round((1000.00 - 10.00) * random.nextDouble() * 100.0) / 100.0;
        tiempo = random.nextInt(100 - 1 + 1) + 1;
        prioridad = random.nextInt(19 - -20 + 1) + -20;
    }

   public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMemoria() {
        return memoria;
    }

    public void setMemoria(double memoria) {
        this.memoria = memoria;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

}

class Sistema{
    
    Graficos ventana;private List<Proceso> procesosOriginales;
    private Queue<Proceso>[] colasPorPrioridad;
    private Semaphore mutex;
        //Variables del procesamiento
        int dormir;
        int ram;
        int tiempoGen;
    

    public Sistema(Graficos ventanaM){
        procesosOriginales = new ArrayList<>();
        colasPorPrioridad = new LinkedList[40];
        ventana = ventanaM;
        mutex = new Semaphore(1);
        ram = 800;
        dormir = 15;
        tiempoGen = 600;

        // Inicializar las colas en cada posición del array
        for (int i = 0; i < colasPorPrioridad.length; i++) {
            colasPorPrioridad[i] = new LinkedList<>();
        }
    }

    public void agregarProceso(Proceso proceso){
        //adquirir semaforo
        try {
        mutex.acquire();
        }
         catch (Exception e) {
        }
            procesosOriginales.add(proceso); 
        try {
        mutex.release();
        }
         catch (Exception e) {
        }
        //liberar semaforo
            int prioridad = proceso.getPrioridad();
            colasPorPrioridad[prioridad + 20].add(proceso);
            ventana.agregarProceso(proceso);
            try{
                Thread.sleep(tiempoGen);
            } 
            catch (Exception e) {
            }
    }

    public void eliminarProcesoLista(String nombreProcesoAQuitar){
        
        Iterator<Proceso> iterador = procesosOriginales.iterator();
       
        //Adquirir semaforo
        try {
    mutex.acquire();
        }
        catch (Exception e) {
        }
        while (iterador.hasNext()) {
            Proceso proceso = iterador.next();
            if (proceso.getNombre().equals(nombreProcesoAQuitar)) {
                iterador.remove();
                break;  
    }
}
    //liberar semaforo
    try{
    mutex.release();
    }
     catch (Exception e) {
        }
    }

    public synchronized void ejecutarProceso(){
        int tiempo;

        for (int prioridad = 0; prioridad < colasPorPrioridad.length; prioridad++) {
            Queue<Proceso> colaActual = colasPorPrioridad[prioridad];

            if (!colaActual.isEmpty()) {
                Proceso proceso = colaActual.poll();
                if (ram < proceso.getMemoria()){
                    //Agregar a exclusiones
                    ventana.borrarEjecu();
                    ventana.agregarEx(proceso);
                    eliminarProcesoLista(proceso.getNombre());
                    ventana.actualizarProcesos(procesosOriginales);
                }else {
                    tiempo = proceso.getTiempo();
                ventana.agregarEjecu(proceso);
                eliminarProcesoLista(proceso.getNombre());
                ventana.actualizarProcesos(procesosOriginales);
                try{
                Thread.sleep(tiempo * dormir);
                } 
            catch (Exception e) {
            }
                
                
                ventana.borrarEjecu();
                ventana.agregarHecho(proceso);
                break;
                }
                
            }
        }
    }

    public void setRam(int r){
        ram = r;
    }

    public void setTiempo(int r){
        dormir = r;
    }

    public void setGenerar(int r){
        tiempoGen = r;
    }

}

class HiloProcesos extends Thread{
    private Sistema sistema;
    private Graficos graficos;
    int i;

    public HiloProcesos(Sistema sistem, Graficos grafics){
        sistema = sistem;
        graficos = grafics;
        i=1;
    }

    @Override
 public void run() {
        while(true) {
            Proceso proceso = new Proceso(i);
            sistema.agregarProceso(proceso);
            i++;
        }
 }

}

class HiloEjecutar extends Thread{
    private Sistema sistema;
    private Graficos graficos;

    public HiloEjecutar(Sistema sistem, Graficos grafics){
        sistema = sistem;
        graficos = grafics;

    }

    @Override
 public void run() {
        while(true) {
            sistema.ejecutarProceso();
        }
    }
}