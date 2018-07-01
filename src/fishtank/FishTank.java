package fishtank;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics2D;  
import java.awt.RenderingHints;  
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.event.*;
import javax.swing.ImageIcon;

final public class FishTank {

    JFrame frame;
    DrawPanel drawPanel;

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;
    
    fish[] fish_array = new fish[4];
    fish[] fish_array_blue = new fish[4];
    
    bubble bubble_string = new bubble();
    
    Cat catk = new Cat();
    
    public static void main(String[] args) {
        new FishTank().go();
    }
    
    private void go() {
        frame = new JFrame("FishTank");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();
        
        for(int i = 0; i < 4; i++){
            fish_array[i] = new fish(new ImageIcon("02.gif").getImage(),
                    310+(int)(Math.random()*512),250+(int)(Math.random()*244),
                    new ImageIcon("02_left.gif").getImage()); //888 - 316 - 60 = 512     554 - 250 - 60 = 244
            fish_array[i].start();
        }
        
        for(int i = 0; i < 4; i++){
            fish_array_blue[i] = new fish(new ImageIcon("03.gif").getImage(),
                    310+(int)(Math.random()*512),250+(int)(Math.random()*244),
                    new ImageIcon("03_left.gif").getImage()); //888 - 316 - 60 = 512     554 - 250 - 60 = 244
            fish_array_blue[i].start();
        }

        bubble_string.start();
        catk.start();
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(1200, 800);
        frame.setLocation(320, 115);
        for(int i = 0; i < 4; i++){
            fish_array[i].run();
        }
        
        for(int i = 0; i < 4; i++){
            fish_array_blue[i].run();
        }
        
        bubble_string.run();
    }
    
    public class fish extends Thread{
        Image img;
        int x_pos;
        int y_pos;
        char direction;
        //Store image as backup
        Image img_r; 
        Image img_l;
        
        //Constructor to realize initialization
        public fish(Image im_right, int xp, int yp,Image im_left){
            x_pos = xp;
            y_pos = yp;
            img_r = im_right;
            img_l = im_left;
            if(x_pos>566){
                img = im_left;
                direction = 'l';
            }
            else{
                img = im_right;
                direction = 'r';
            }
        }
        
        @Override
        public void run(){
            while(true){
                /* Use the Index after operator '+=' and '-='
                and the parameter in the Thread.sleep() function
                to control the fishs' moving speed 
                */
                if('l' == direction && x_pos >324) //move to the left
                    x_pos-=5;
                else if('r' == direction && x_pos <828) //move to the right [888 - 60]
                    x_pos+=5;
                else if('l' == direction && x_pos <= 324){ //Critical and turning direction
                    img = img_r;
                    direction = 'r';
                }
                else if('r' == direction && x_pos >= 828){ //Critical and turning direction
                    img = img_l;
                    direction = 'l';
                }
                try{
                    Thread.sleep(100);
                }catch (Exception exc){
                    System.out.println ("Exception is caught");
                }
                frame.repaint();
            }
        }
    }
    
    public class bubble extends Thread{
        int[] x_pos = new int[]{367,356,365,349,360,338};
        int[] y_pos = new int[]{500,475,442,397,341,265};
        int[] diameter = new int[]{15,20,28,38,56,70};
        int leap = 0;
        
        public void run(){
            while(true){
                frame.repaint();
                    if(leap<=5)
                        leap++;
                    else{
                        leap = 0;
                    }
                    try{
                        Thread.sleep(300);
                    }catch (Exception exc){
                            System.out.println ("Exception is caught");
                    }
                }
            }
    }
    
    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {            
            g.drawImage(new ImageIcon("Picture1.png").getImage(), 196, 126, 127, 505, this);
            g.drawImage(new ImageIcon("Picture2.png").getImage(), 315, 205, 566, 405, this);
            g.drawImage(new ImageIcon("stone.png").getImage(), 325, 480, 120, 80, this);
            g.drawImage(flip("stone.png"), 600, 440, 80, 50, this);
            g.drawImage(new ImageIcon("shell.gif").getImage(), 720, 465, 120,80,this);
            
            for(int i = 0; i < 4; i++)
                g.drawImage(fish_array[i].img, fish_array[i].x_pos,fish_array[i].y_pos, 60, 60, this);
            for(int i = 0; i < 4; i++)
                g.drawImage(fish_array_blue[i].img, fish_array_blue[i].x_pos,fish_array_blue[i].y_pos, 60, 60, this);
            
            Graphics2D g2 = (Graphics2D) g;
            BasicStroke stroke = new BasicStroke(2);
            g2.setStroke(stroke);
            
            //Draw the rising bubble
            for(int i = 0; i < bubble_string.leap; i++){
                BasicStroke stroke1 = new BasicStroke(3);
                g2.setStroke(stroke1);
                g2.setColor(new Color(255,255,255,180));
                g2.fillOval(bubble_string.x_pos[i], bubble_string.y_pos[i], bubble_string.diameter[i], bubble_string.diameter[i]);
                g2.setColor(new Color(232,232,232));
                g2.drawOval(bubble_string.x_pos[i], bubble_string.y_pos[i], bubble_string.diameter[i], bubble_string.diameter[i]);
                BasicStroke stroke2 = new BasicStroke(2);
                g2.setStroke(stroke2);
                g2.setColor(new Color(254,253,255));
                g2.drawArc(bubble_string.x_pos[i]+(bubble_string.diameter[i]/8), 
                        bubble_string.y_pos[i]+(bubble_string.diameter[i]/8), 
                        bubble_string.diameter[i], bubble_string.diameter[i],118,50);
            }
            
            //Surface [I] and simulate shading
            g2.setColor(Color.BLACK);
            int[] xPoints1 = {320,880,880,320};
            int[] yPoints1 = {600,560,200,240};
            g2.drawPolygon(xPoints1,yPoints1,4);
            g.setColor(new Color(134,132,135));
            g.drawLine(882, 200, 882, 560);
            g.setColor(new Color(70,100,82));
            g.drawLine(318, 600, 318, 240);
        
            //Surface [II] and simulate shading
            g2.setColor(Color.BLACK);
            int[] xPoints2 = {320,200,200,320};
            int[] yPoints2 = {240,160,520,600};
            g2.drawPolygon(xPoints2,yPoints2,4);
            g.setColor(new Color(134,132,135));
            g.drawLine(198, 160, 198, 520);
            
            //Base [III] and Gradient fill
            int[] xPoints3 = {316,192,192,316};
            int[] yPoints3 = {596,512,552,636};
            GradientPaint paint = new GradientPaint(192,520,new Color(66,66,68),244,596,new Color(95,95,98), true);  
            g2.setPaint(paint);
            g2.fillPolygon(xPoints3,yPoints3,4);
            g.setColor(new Color(134,132,135));
            g.drawLine(196, 516, 316, 596);
            
            //Base [IV] and Gradient fill
            g.setColor(new Color(8,5,20));
            int[] xPoints4 = {316,888,888,316};
            int[] yPoints4 = {596,554,590,636};
            GradientPaint paint_ = new GradientPaint(316,596,new Color(8,5,20), 888,600,new Color(6,5,10), true);
            g2.setPaint(paint_);
            g2.fillPolygon(xPoints4,yPoints4,4);
            g.setColor(new Color(134,132,135));
            g.drawLine(400, 590, 888, 554);
            
            //Top Eage [V] and Gradient fill
            int[] xPoints5 = {324,192,192,324};
            int[] yPoints5 = {208,122,162,250};
            GradientPaint paint1 = new GradientPaint(192,122,new Color(66,66,68), 244,240,new Color(95,95,98), true);  
            g2.setPaint(paint1);
            g2.fillPolygon(xPoints5,yPoints5,4);

            //Top Eage [VI] and Gradient fill
            g.setColor(new Color(8,5,20));
            int[] xPoints6 = {324,888,888,324};
            int[] yPoints6 = {208,180,216,250}; //CHANGE TWO INDEX! FIRST AND LAST
            GradientPaint paint2 = new GradientPaint(162,110,new Color(59,59,61), 444,108,new Color(19,18,23), true);
            g2.setPaint(paint2);
            g2.fillPolygon(xPoints6,yPoints6,4);

            //Top Surface [VII] and Gradient fill
            int[] xPoints7 = {200,220,328,328};
            int[] yPoints7 = {124,80,142,208};
            GradientPaint paint3 = new GradientPaint(192,520,new Color(66,66,68), 244,596,new Color(95,95,98), true);  
            g2.setPaint(paint3);
            g2.fillPolygon(xPoints7,yPoints7,4);
            g.setColor(new Color(120,120,120));
            g.drawLine(193, 122, 324, 205);
        
            //the shading between [V] and [VII]
            g.setColor(new Color(120,120,120));
            g.drawLine(204, 127, 324, 208);
            
            //Top Surface [VIII] and Gradient fill
            int[] xPoints8 = {328,846,888,328};
            int[] yPoints8 = {142,124,176,206};
            GradientPaint paint4 = new GradientPaint(324,220,new Color(59,59,61), 888,216,new Color(19,18,23), true);
            g2.setPaint(paint4);
            g2.fillPolygon(xPoints8,yPoints8,4);
            g.setColor(new Color(120,120,120));
            g.drawLine(326, 206, 888, 178);

            //the shading between [VI] and [VIII]
            g.setColor(new Color(120,120,120));
            g.drawLine(326, 206, 888, 180);

            //Top Surface
            int[] xPoints9 = {220,328,846,666};
            int[] yPoints9 = {80,142,124,66};
            GradientPaint paint5 = new GradientPaint(328,142,new Color(132,130,128), 700,70,new Color(90,95,95), true);
            g2.setPaint(paint5);
            g2.fillPolygon(xPoints9,yPoints9,4);
            g.setColor(new Color(120,120,120));
            g.drawLine(322, 207, 888, 180);
            g.setColor(new Color(120,120,120));
            g.drawLine(322, 207, 888, 177);
            
            //Draw the cat knight and its Hook
            g.drawImage(new ImageIcon("cat_knight.png").getImage(), catk.x_pos, catk.y_pos,90,135, this);
            g.drawImage(new ImageIcon("hook.png").getImage(), catk.x_pos_hook, catk.y_pos_hook,30,70, this);
        }
    }
   
    public class Cat extends Thread{
        int x_pos;
        int y_pos;
        int x_pos_hook;
        int y_pos_hook;
        final int hook_speed = 7;
        final int step = 1;
        boolean lock = false;
        
        //constructor to assign initial value
        public Cat(){
            x_pos = 500;
            y_pos = 0;
            x_pos_hook = x_pos;
            y_pos_hook = y_pos + 240;
        }
        
        public void run(){
            while(true){
                if(!(lock)){
                    myEvent();
                    frame.repaint();
                }

                if(lock){
                    while(y_pos_hook<=424){ //494[end of fish appearing area] - 70
                            y_pos_hook += hook_speed;
                            try{
                            Thread.sleep(100);
                            }catch (Exception exc){
                            System.out.println ("Exception is caught");
                            frame.repaint();
                            }
                    }
                    while(y_pos_hook>240){ //494[end of fish appearing area] - 70
                            y_pos_hook -= hook_speed;
                            try{
                            Thread.sleep(50);
                            }catch (Exception exc){
                            System.out.println ("Exception is caught");
                            frame.repaint();
                            }
                    }
                    lock = false;
                }
                
                try{
                    Thread.sleep(300);
                }catch (Exception exc){
                    System.out.println ("Exception is caught");
                }
            }
        }
        
        private void myEvent()
        {
            frame.addKeyListener(new KeyAdapter()//键盘监听按钮
            {
                @Override
                public void keyPressed(KeyEvent e)
                {
                    if(e.getKeyCode()==KeyEvent.VK_LEFT){
                        x_pos-=step;
                        x_pos_hook = x_pos;
                        System.out.println("left<-- : x_pos = " + x_pos);
                        e = null;
                    }
                    else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                        x_pos+=step;
                        x_pos_hook = x_pos;
                        System.out.println("right--> x_pos = " + x_pos);
                        e = null;
                    }
                    else if(e.getKeyCode()==KeyEvent.VK_ENTER){
                        lock = true;
                    }
                    else
                        e.consume();
                }

            });
        }
    }
    public BufferedImage flip(String inputImageFilename) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImageFilename));
            BufferedImage outputImage = flipImage(inputImage);
            return outputImage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        
    /** 
     * Horizontal flip image
     *  
     * @param bufferedimage 
     *           Target Image 
     * @return 
     */  
    public BufferedImage flipImage(final BufferedImage bufferedimage) {  
        int w = bufferedimage.getWidth();  
        int h = bufferedimage.getHeight();  
        BufferedImage img;  
        Graphics2D graphics2d;  
        (graphics2d = (img = new BufferedImage(w, h, bufferedimage  
                .getColorModel().getTransparency())).createGraphics())  
                .drawImage(bufferedimage, 0, 0, w, h, w, 0, 0, h, null);  
        graphics2d.dispose();  
        return img;  
    }    
}