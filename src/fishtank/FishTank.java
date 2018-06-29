package fishtank;

import javax.swing.*;
import java.awt.*;
import java.applet.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics2D;  
import java.awt.RenderingHints;  
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.event.*;
import javax.swing.ImageIcon;

final public class Tester {

    JFrame frame;
    DrawPanel drawPanel;

    private int oneX = 7;
    private int oneY = 7;

    boolean up = false;
    boolean down = true;
    boolean left = false;
    boolean right = true;

    public static void main(String[] args) {
        new Tester().go();
    }
    
    private void go() {
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(300, 300);
        frame.setLocation(375, 55);
        moveIt();
    }

    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        int[] xPoints1 = {160,440,440,160};
        int[] yPoints1 = {300,280,100,120};
        g.drawPolygon(xPoints1,yPoints1,4);
        g.setColor(new Color(134,132,135));
        g.drawLine(441, 100, 441, 280);
        g.setColor(new Color(35,50,41));
        g.drawLine(158, 300, 158, 120);
        
        g.setColor(Color.BLACK);
        int[] xPoints2 = {160,100,100,160};
        int[] yPoints2 = {120,80,260,300};
        g.drawPolygon(xPoints2,yPoints2,4);
        g.setColor(new Color(134,132,135));
        g.drawLine(99, 80, 99, 260);
        
        int[] xPoints3 = {158,96,96,158};
        int[] yPoints3 = {298,256,276,318};
        GradientPaint paint = new GradientPaint(96,260,new Color(66,66,68), 122,298,new Color(95,95,98), true);  
        g2.setPaint(paint);
        g2.fillPolygon(xPoints3,yPoints3,4);
        g.setColor(new Color(134,132,135));
        g.drawLine(98, 258, 158, 298);
        
        g.setColor(new Color(8,5,20));
        int[] xPoints4 = {158,444,444,158};
        int[] yPoints4 = {298,277,295,318};
        GradientPaint paint_ = new GradientPaint(158,298,new Color(8,5,20), 444,300,new Color(6,5,10), true);
        g2.setPaint(paint_);
        g2.fillPolygon(xPoints4,yPoints4,4);
        g.setColor(new Color(134,132,135));
        g.drawLine(200, 296, 444, 277);
        
        int[] xPoints5 = {162,96,96,162};
        int[] yPoints5 = {104,60,81,125};
        GradientPaint paint1 = new GradientPaint(96,260,new Color(66,66,68), 122,298,new Color(95,95,98), true);  
        g2.setPaint(paint1);
        g2.fillPolygon(xPoints5,yPoints5,4);
        
        g.setColor(new Color(8,5,20));
        int[] xPoints6 = {162,444,444,162};
        int[] yPoints6 = {103,90,108,124};
        GradientPaint paint2 = new GradientPaint(162,110,new Color(59,59,61), 444,108,new Color(19,18,23), true);
        g2.setPaint(paint2);
        g2.fillPolygon(xPoints6,yPoints6,4);
        
        int[] xPoints7 = {100,110,164,164};
        int[] yPoints7 = {61,39,70,103};
        GradientPaint paint3 = new GradientPaint(96,260,new Color(66,66,68), 122,298,new Color(95,95,98), true);  
        g2.setPaint(paint3);
        g2.fillPolygon(xPoints7,yPoints7,4);
        g.setColor(new Color(225,231,231));
        g.drawLine(110, 68, 162, 104);
        
        g.setColor(new Color(200,200,159));
        g.drawLine(102, 62, 162, 104);
        
        int[] xPoints8 = {162,423,444,162};
        int[] yPoints8 = {71,60,105,101};
        GradientPaint paint4 = new GradientPaint(162,110,new Color(59,59,61), 444,108,new Color(19,18,23), true);
        g2.setPaint(paint4);
        g2.fillPolygon(xPoints8,yPoints8,4);
        g.setColor(new Color(182,180,155));
        g.drawLine(161, 102, 438, 90);
        g.setColor(new Color(182,180,155));
        g.drawLine(161, 102, 438, 88);
        
        int[] xPoints9 = {110,164,425,333};
        int[] yPoints9 = {39,71,62,33};
        GradientPaint paint5 = new GradientPaint(164,71,new Color(158,152,147), 350,35,new Color(121,121,121), true);
        g2.setPaint(paint5);
        g2.fillPolygon(xPoints9,yPoints9,4);
        g.setColor(new Color(182,180,155));
        g.drawLine(161, 102, 438, 90);
        g.setColor(new Color(182,180,155));
        g.drawLine(161, 102, 438, 88);
        }
    }

    private void moveIt() {
        while(true){
            if(oneX >= 283){
                right = false;
                left = true;
            }
            if(oneX <= 7){
                right = true;
                left = false;
            }
            if(oneY >= 259){
                up = true;
                down = false;
            }
            if(oneY <= 7){
                up = false;
                down = true;
            }
            if(up){
                oneY--;
            }
            if(down){
                oneY++;
            }
            if(left){
                oneX--;
            }
            if(right){
                oneX++;
            }
            try{
                Thread.sleep(10);
            } catch (Exception exc){}
            frame.repaint();
        }
    }

}

/*
public class FishTank extends Applet implements ActionListener{
    
    Button button1;
    boolean drawfish = false;
    final int FEED_INITIAL_POSITION_X = 260;
    final int FEED_INITIAL_POSITION_Y = 120;
    final int BOTTOM = 270;
    int times = 0;
    feed myfeed = new feed();
    fish[] fish_array = new fish[15];
    
    public void init() {
        this.setSize(600,600);
	setLayout(null);
        Font mf = new Font("Courier",Font.BOLD,12);
        this.setFont(mf);
        button1 = new Button("Feeding!");
        button1.setBounds(240,350,60,40);
        add(button1);
        button1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1 && !(drawfish)){
            drawfish = true;
            repaint(135,100,292,180);
            try {
                Thread.sleep(100);
                } catch (InterruptedException interrupt){
                interrupt.printStackTrace();
                }
        }
        else
            System.out.println("Button 2 was pressed");
    }

    public class feed{
        int x_pos = FEED_INITIAL_POSITION_X;
        int y_pos = FEED_INITIAL_POSITION_Y;
        int move_distance = 10;        
        public void feedMove(){
            Thread thread = new Thread();
            thread.start();
            while(y_pos < BOTTOM){
                y_pos = y_pos + move_distance;
                repaint();
                try {
                Thread.sleep(1000);
                } catch (InterruptedException e){
                e.printStackTrace();
                }
            }
        }
    }
    public class fish{
        Image img;
        int x_pos;
        int y_pos;
        public fish(Image im, int xp, int yp,String filename){
            img = im;
            x_pos = xp;
            y_pos = yp;
            Image ima = null;
            if(x_pos<275){
                ima = flip(filename);
                img = new ImageIcon(ima).getImage();
            }
        }
        public void fish_move(){
            
        }
    }
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(2);
        g2.setStroke(stroke);
        g.drawString("Only 1 time! Too much feed will affect fish health", 310, 375);
        
        super.paint(g);
        g.drawImage(new ImageIcon("timg2.png").getImage(), 153, 101, 294, 204, this);
        g.drawImage(new ImageIcon("timg3.png").getImage(), 98, 63, 67, 257, this);
        
        if(true == drawfish){
            g.drawImage(new ImageIcon("feed.png").getImage(), myfeed.x_pos, myfeed.y_pos+times*100, 50,30,this);
            for(int i = 0; i < 15; i++){
                fish_array[i] = new fish(new ImageIcon("fish.png").getImage(),135+(int)(Math.random()*292),100+(int)(Math.random()*180),"fish.png");
                g.drawImage(fish_array[i].img, fish_array[i].x_pos,fish_array[i].y_pos, 25, 20, this);
            }
            times++;
        }
        
        g2.setColor(Color.BLACK);
        int[] xPoints1 = {160,440,440,160};
        int[] yPoints1 = {300,280,100,120};
        g2.drawPolygon(xPoints1,yPoints1,4);
        g.setColor(new Color(134,132,135));
        g.drawLine(441, 100, 441, 280);
        g.setColor(new Color(35,50,41));
        g.drawLine(158, 300, 158, 120);
        
        g2.setColor(Color.BLACK);
        int[] xPoints2 = {160,100,100,160};
        int[] yPoints2 = {120,80,260,300};
        g2.drawPolygon(xPoints2,yPoints2,4);
        g.setColor(new Color(134,132,135));
        g.drawLine(99, 80, 99, 260);
        
        int[] xPoints3 = {158,96,96,158};
        int[] yPoints3 = {298,256,276,318};
        GradientPaint paint = new GradientPaint(96,260,new Color(66,66,68), 122,298,new Color(95,95,98), true);  
        g2.setPaint(paint);
        g2.fillPolygon(xPoints3,yPoints3,4);
        g.setColor(new Color(134,132,135));
        g.drawLine(98, 258, 158, 298);
        
        g.setColor(new Color(8,5,20));
        int[] xPoints4 = {158,444,444,158};
        int[] yPoints4 = {298,277,295,318};
        GradientPaint paint_ = new GradientPaint(158,298,new Color(8,5,20), 444,300,new Color(6,5,10), true);
        g2.setPaint(paint_);
        g2.fillPolygon(xPoints4,yPoints4,4);
        g.setColor(new Color(134,132,135));
        g.drawLine(200, 296, 444, 277);
        
        int[] xPoints5 = {162,96,96,162};
        int[] yPoints5 = {104,60,81,125};
        GradientPaint paint1 = new GradientPaint(96,260,new Color(66,66,68), 122,298,new Color(95,95,98), true);  
        g2.setPaint(paint1);
        g2.fillPolygon(xPoints5,yPoints5,4);
        
        g.setColor(new Color(8,5,20));
        int[] xPoints6 = {162,444,444,162};
        int[] yPoints6 = {103,90,108,124};
        GradientPaint paint2 = new GradientPaint(162,110,new Color(59,59,61), 444,108,new Color(19,18,23), true);
        g2.setPaint(paint2);
        g2.fillPolygon(xPoints6,yPoints6,4);
        
        int[] xPoints7 = {100,110,164,164};
        int[] yPoints7 = {61,39,70,103};
        GradientPaint paint3 = new GradientPaint(96,260,new Color(66,66,68), 122,298,new Color(95,95,98), true);  
        g2.setPaint(paint3);
        g2.fillPolygon(xPoints7,yPoints7,4);
        g.setColor(new Color(225,231,231));
        g.drawLine(110, 68, 162, 104);
        
        g.setColor(new Color(200,200,159));
        g.drawLine(102, 62, 162, 104);
        
        int[] xPoints8 = {162,423,444,162};
        int[] yPoints8 = {71,60,105,101};
        GradientPaint paint4 = new GradientPaint(162,110,new Color(59,59,61), 444,108,new Color(19,18,23), true);
        g2.setPaint(paint4);
        g2.fillPolygon(xPoints8,yPoints8,4);
        g.setColor(new Color(182,180,155));
        g.drawLine(161, 102, 438, 90);
        g.setColor(new Color(182,180,155));
        g.drawLine(161, 102, 438, 88);
        
        int[] xPoints9 = {110,164,425,333};
        int[] yPoints9 = {39,71,62,33};
        GradientPaint paint5 = new GradientPaint(164,71,new Color(158,152,147), 350,35,new Color(121,121,121), true);
        g2.setPaint(paint5);
        g2.fillPolygon(xPoints9,yPoints9,4);
        g.setColor(new Color(182,180,155));
        g.drawLine(161, 102, 438, 90);
        g.setColor(new Color(182,180,155));
        g.drawLine(161, 102, 438, 88);
        
//        Toolkit t = Toolkit.getDefaultToolkit();
//        Image i = t.getImage("timg1.png");
//        BufferedImage bi=new BufferedImage(i.getWidth(null),i.getHeight(null),BufferedImage.TYPE_INT_ARGB);
//        /*BufferedImage bi = toBufferedImage(i);
//        /*BufferedImage bi1 = flipImage(bi);
//        Graphics big = bi1.createGraphics(); 
//        g.drawImage(bi, 120, 100, this);
        
    }
   
    public static BufferedImage toBufferedImage(Image image) {  
        if (image instanceof BufferedImage) {  
             return (BufferedImage)image;  
        }                   
        // 加载所有像素 
        image = new ImageIcon(image).getImage();                    
        BufferedImage bimage = null;  
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
        try {                        
             int transparency = Transparency.OPAQUE;                        
             // 创建buffer图像  
             GraphicsDevice gs = ge.getDefaultScreenDevice();  
             GraphicsConfiguration gc = gs.getDefaultConfiguration();  
             bimage = gc.createCompatibleImage(  
             image.getWidth(null), image.getHeight(null), transparency);  
        } catch (HeadlessException e) {  
              e.printStackTrace(); 
        }                   
        if (bimage == null) {                         
            int type = BufferedImage.TYPE_INT_ARGB;  
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type); 
        }                   
        // 复制
        Graphics g = bimage.createGraphics();                   
        // 赋值  
        g.drawImage(image,0,0,null);  
        g.dispose();                    
        return bimage;
    } 
    
        /** 
         * 旋转图片为指定角度 
         *  
         * @param bufferedimage 
         *            目标图像 
         * @param degree 
         *            旋转角度 
         * @return 
         */  
        public BufferedImage rotateImage(final BufferedImage bufferedimage,  
                final int degree) {  
            int w = bufferedimage.getWidth();  
            int h = bufferedimage.getHeight();  
            int type = bufferedimage.getColorModel().getTransparency();  
            BufferedImage img;  
            Graphics2D graphics2d;  
            (graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())  
                    .setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
            graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);  
            graphics2d.drawImage(bufferedimage, 0, 0, null);  
            graphics2d.dispose();  
            return img;  
        }  

        /** 
         * 变更图像为指定大小 
         *  
         * @param bufferedimage 
         *            目标图像 
         * @param w 
         *            宽 
         * @param h 
         *            高 
         * @return 
         */  
        public  BufferedImage resizeImage(final BufferedImage bufferedimage,  
                final int w, final int h) {  
            int type = bufferedimage.getColorModel().getTransparency();  
            BufferedImage img;  
            Graphics2D graphics2d;  
            (graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())  
                    .setRenderingHint(RenderingHints.KEY_INTERPOLATION,  
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
            graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0,  
                    bufferedimage.getWidth(), bufferedimage.getHeight(), null);  
            graphics2d.dispose();  
            return img;  
        }  

        /** 
         * 水平翻转图像 
         *  
         * @param bufferedimage 
         *            目标图像 
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
        
    public BufferedImage flip(String inputImageFilename) {

        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImageFilename));
            BufferedImage outputImage = flipImage(inputImage);
            return outputImage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void rotate(String inputImageFilename, double angle, String outputImageFilename) {

        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImageFilename));
            BufferedImage outputImage = rotateImage(inputImage, angle);
            ImageIO.write(outputImage, "PNG", new File(outputImageFilename));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedImage rotateImage(BufferedImage sourceImage, double angle) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        BufferedImage destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = destImage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.rotate(angle / 180 * Math.PI, width / 2 , height / 2);
        g2d.drawRenderedImage(sourceImage, transform);

        g2d.dispose();
        return destImage;
    }
}
*/