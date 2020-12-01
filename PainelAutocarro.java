/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autocarro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

/**
 *
 * @author creuma
 */
public class PainelAutocarro extends JPanel implements Runnable {

   private Thread thread; 
   private int xPosition = 20, xMoviment=0, xMovimentInside=0;
   private AffineTransform atf; 
   private float angulo = 0;
   private boolean flagXMovementInside = true;
   private Ellipse2D circuloPneu1;
   private Ellipse2D circuloPneu1Interior;
   private Ellipse2D circuloPneu2;
   private Ellipse2D circuloPneu2Interior;

   //Construtor
   public PainelAutocarro() {
           
     new Thread(this).start();
  
   }
   
   // Getters e Setters
    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getxMoviment() {
        return xMoviment;
    }

    public void setxMoviment(int xMoviment) {
        this.xMoviment = xMoviment;
    }

    public int getxMovimentInside() {
        return xMovimentInside;
    }

    public void setxMovimentInside(int xMovimentInside) {
        this.xMovimentInside = xMovimentInside;
    }

    public float getAngulo() {
        return angulo;
    }

    public void setAngulo(float angulo) {
        this.angulo = angulo;
    }

    public boolean isFlagXMovementInside() {
        return flagXMovementInside;
    }

    public void setFlagXMovementInside(boolean flagXMovementInside) {
        this.flagXMovementInside = flagXMovementInside;
    }
   
   //Método responsável por pintar 
   public void paintComponent(Graphics g){

       super.paintComponent(g);
       Graphics2D g2d = (Graphics2D) g;
       
       // Rectângulo que representa a estrada
       g2d.setPaint(new Color(0,0,0));
       g2d.fillRect(0,277,700 , 70);
       
       // Detalhes brancos da estrada
       for(int i=0; i<6; i++)
       {
           g2d.setPaint(new Color(255,255,255));
           g2d.fillRect(getxPosition(), 305, 80, 10);
           setxPosition(getxPosition()+110);
           if(i==5){
               setxPosition(20);
           }
       }    
       
       atf = new AffineTransform();
       atf.translate(xMoviment, 0);
       g2d.setTransform(atf);
       
       //Rectângulos que representam o autocarro
       //Rectângulo 1 ( maior)
       
       g2d.setPaint(new Color(37,153,179));
       g2d.fillRect(30,120, 470, 180);
       
       //g2d.fill(new Ellipse2D.Double(30,50,20,20));
       
       //Rectângulo 2 (menor)
       g2d.setPaint(new Color(37,153,179));
       g2d.fillRect(500,220, 70, 80);
       
       // Janelas do autocarro
       //Janela 1
       g2d.setPaint(new Color(255,255,255));
       g2d.fillRect(60,180, 90, 40);
      // g2d.setPaint(new Color(255,165,0));
       //g2d.fillOval(70,190,15,15);
       
       //Janela 2
       g2d.setPaint(new Color(255,255,255));
       g2d.fillRect(160,180, 90, 40);
       
       //Janela 3
       g2d.setPaint(new Color(255,255,255));
       g2d.fillRect(260,180, 90, 40);
       
       //Porta do autocarro
       g2d.setPaint(new Color(255,255,255));
       g2d.fillRect(380,138, 90, 150);
       
       //Circulo interior da janela
       // 65,190,15,15
       
       g2d.setPaint(new Color(255,165,0));
       g2d.fillOval(65+xMovimentInside, 201,15,15);
       
        //Linhas do autocarro
       //Linha 1
       g2d.setPaint(new Color(255,255,255));
       g2d.fillRect(60,240, 290, 5);
       
       //Linha 2
       g2d.setPaint(new Color(255,255,255));
       g2d.fillRect(60,248, 290, 5);
       
       //Pneus do autocarro
       //Pneu 1
       
       atf = new AffineTransform();
       atf.translate(xMoviment,0);
       g2d.setTransform(atf);
       
       circuloPneu1 = new Ellipse2D.Double(40,280,50, 50);
       g2d.setPaint(new Color(128,128,128));
       g2d.fill(circuloPneu1);
       
       atf.rotate(Math.toRadians(angulo), circuloPneu1.getCenterX(),circuloPneu1.getCenterY());
       g2d.setTransform(atf);
     
       circuloPneu1Interior = new Ellipse2D.Double(58,300,15, 15);
       g2d.setPaint(new Color(255,255,255));
       g2d.fill(circuloPneu1Interior);
       
       atf= new AffineTransform();
       atf.translate(xMoviment,0);
       g2d.setTransform(atf);
       
       circuloPneu2 = new Ellipse2D.Double(490,280,50,50);
       g2d.setPaint(new Color(128,128,128));
       g2d.fill(circuloPneu2);
       
       atf.rotate(Math.toRadians(angulo), circuloPneu2.getCenterX(),circuloPneu2.getCenterY());
       g2d.setTransform(atf);
       
       circuloPneu2Interior = new Ellipse2D.Double(508,297,15,15);
       g2d.setPaint(new Color(255,255,255));
       g2d.fill(circuloPneu2Interior);
         
}
   
    @Override
    public void run() {
        
        try {
            
            while(true){
            
            //-600 porque o tamnho do autocarro é 540, dei um espaço de 60 para ter um movimento (smooth) suave    
            if(getxMoviment() > getSize().width)
                setxMoviment(-600);
          
            if(isFlagXMovementInside())
             {
                setxMovimentInside(getxMovimentInside()+1);
                // xMovimentInside > 64, porque a posição válida da bola dentro da janela é de 130(65 da posição da janela
                //+65 da posição da bola
                if(getxMovimentInside() > 64)
                    this.setFlagXMovementInside(false);
             }        
             else{
                   setxMovimentInside(getxMovimentInside()-1);
                   if(getxMovimentInside() < 0)
                        setFlagXMovementInside(true);
                }  
            
            setxMoviment(getxMoviment()+1);
            setAngulo(getAngulo()+5);
            thread.sleep(15);
            super.repaint();
              
            }
            
         }   
        catch (Exception e) {
            System.out.println("Erro!");
        }
        
      

        
    }
    
    
    
}
