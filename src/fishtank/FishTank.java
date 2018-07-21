package fishtank;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.ImageIcon;

final public class FishTank {

    JFrame frame;
    DrawPanel drawPanel;

    //control the press guide display press and bounce
    boolean up = false;
    boolean down = true;

    //create the fish objects
    fish[] fish_array = new fish[4];
    fish[] fish_array_blue = new fish[4];

    //create the bubble object and collision checking object
    bubble bubble_string = new bubble();
    Collision coll_test = new Collision();
	Cat catk = new Cat();
    PressGuide pguide = new PressGuide();

    //control the fish display after the fish is caught by hook
    boolean lost[] = {false, false, false, false};
    boolean lost_blue[] = {false, false, false, false};


    public static void main(String[] args) {
        new FishTank().go();
    }

    private void go() {
        frame = new JFrame("FishTank");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        //initialize the fish objects
        //invoke the constrctor defined inside the fish class
        //random produce the fish's initial position
        for (int i = 0; i < 4; i++) {
            fish_array[i] = new fish(new ImageIcon("02.gif").getImage(),
                    310 + (int) (Math.random() * 512), 250 + (int) (Math.random() * 244),
                    new ImageIcon("02_left.gif").getImage()); //888 - 316 - 60 = 512     554 - 250 - 60 = 244
            fish_array[i].start();
        }

        for (int i = 0; i < 4; i++) {
            fish_array_blue[i] = new fish(new ImageIcon("03.gif").getImage(),
                    310 + (int) (Math.random() * 512), 250 + (int) (Math.random() * 244),
                    new ImageIcon("03_left.gif").getImage()); //888 - 316 - 60 = 512     554 - 250 - 60 = 244
            fish_array_blue[i].start();
        }

        //start threads
        bubble_string.start();
        catk.start();
        coll_test.start();
        pguide.start();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(1200, 800);
        frame.setLocation(320, 115);

        //run the threads
        for (int i = 0; i < 4; i++) {
            fish_array[i].run();
        }

        for (int i = 0; i < 4; i++) {
            fish_array_blue[i].run();
        }
        coll_test.run();
        bubble_string.run();
        pguide.run();

    }

    public class fish extends Thread {

        Image img;
        int x_pos;
        int y_pos;
        char direction;
        //Store image as backup
        Image img_r;
        Image img_l;
        //As a switch to adjust action after hooked
        boolean is_impact;
        //hooked fish move to the water surface, then disappear
        //use to control display in paint function
        boolean disappear = false;

        //Constructor
        /**
         * @<param name="im_right" value="the fish's image facing the right direction">
         * @<param name="xp" value="the x position value assigned">
         * @<param name="yp" value="the y position value assigned">
         * @<param name="im_left" value="the fish's image facing the left direction">
         * @<details>if the position beyond the middle of tank, then use "im_left", vice versa</details>
         * @return
         */
        public fish(Image im_right, int xp, int yp, Image im_left) {
            x_pos = xp;
            y_pos = yp;
            img_r = im_right;
            img_l = im_left;
            if (x_pos > 566) {
                img = im_left;
                direction = 'l';
            } else {
                img = im_right;
                direction = 'r';
            }
        }

        /**
         * Control the movement of the fish at the same depth [normal senario]
         * @ 5 is swim speed, can be changed
         * Control the movement after hooked [is_impact as switch]
         * control the visiblility after hooked to the water surface [Position judgement] 
         */
        @Override
        public void run() {
            while (true) {
                /* Use the Index after operator '+=' and '-='
                and the parameter in the Thread.sleep() function
                to control the fishs' moving speed 
                 */
                if (is_impact == false) {
                    if ('l' == direction && x_pos > 324) //move to the left
                    {
                        x_pos -= 5;
                    } else if ('r' == direction && x_pos < 828) //move to the right [888 - 60]
                    {
                        x_pos += 5;
                    } else if ('l' == direction && x_pos <= 324) { //Critical and turning direction
                        img = img_r;
                        direction = 'r';
                    } else if ('r' == direction && x_pos >= 828) { //Critical and turning direction
                        img = img_l;
                        direction = 'l';
                    }
                    try {
                        Thread.sleep(100);
                    } catch (Exception exc) {
                        System.out.println("Exception is caught");
                    }
                    frame.repaint();
                } else {
                    y_pos = catk.y_pos_hook + 40;
                    x_pos = catk.x_pos_hook;
                    try {
                        Thread.sleep(100);
                    } catch (Exception exc) {
                        System.out.println("Exception is caught");
                    }
                    frame.repaint();
                    if (y_pos == 280 && x_pos == catk.x_pos_hook) {
                        disappear = true;
                        break;
                    }
                }

            }
        }
    }

    public class bubble extends Thread {

        int[] x_pos = new int[]{367, 356, 365, 349, 360, 338};
        int[] y_pos = new int[]{500, 475, 442, 397, 341, 265};
        int[] diameter = new int[]{15, 20, 28, 38, 56, 70};
        //control the number of bubble disappear
        int leap = 0;

        public void run() {
            while (true) {
                frame.repaint();
                if (leap <= 5) {
                    leap++;
                } else {
                    leap = 0;
                }
                try {
                    Thread.sleep(300);
                } catch (Exception exc) {
                    System.out.println("Exception is caught");
                }
            }
        }
    }

    class PressGuide extends Thread {

        KeyButton[] KeyArray = {
            new KeyButton("←", 950, 130),
            new KeyButton("→", 950, 230),
            new KeyButton("Enter", 950, 330)
        };

        public void run() {
            while (true) {

                for (int i = 0; i < 3; i++) {
                    frame.repaint();
                    try {
                        Thread.sleep(500);
                    } catch (Exception exc) {
                        System.out.println("Exception is caught");
                    }
                    KeyArray[i].concave = false;
                }
            }
        }
    }

    class KeyButton {

        boolean concave = false; //凹
        String content = null;
        int x_pos;
        int y_pos;

        public KeyButton(String str, int x, int y) {
            content = str;
            x_pos = x;
            y_pos = y;
        }
    }

    class DrawPanel extends JPanel {

        public void paintComponent(Graphics g) {
            g.drawImage(new ImageIcon("Picture1.png").getImage(), 196, 126, 127, 505, this);
            g.drawImage(new ImageIcon("Picture2.png").getImage(), 315, 205, 566, 405, this);
            g.drawImage(new ImageIcon("stone.png").getImage(), 325, 480, 120, 80, this);
            g.drawImage(flip("stone.png"), 600, 440, 80, 50, this);
            g.drawImage(new ImageIcon("shell.gif").getImage(), 720, 465, 120, 80, this);

            for (int i = 0; i < 4; i++) {
                if ((fish_array[i].disappear == false && lost[i] == false) || (fish_array[i].disappear == false && lost[i] == true)) {
                    g.drawImage(fish_array[i].img, fish_array[i].x_pos, fish_array[i].y_pos, 60, 60, this);
                    //g.drawRect(fish_array[i].x_pos, fish_array[i].y_pos, 60, 60);
                }
            }
            for (int i = 0; i < 4; i++) {
                if ((fish_array_blue[i].disappear == false && lost[i] == false) || (fish_array_blue[i].disappear == false && lost[i] == true)) {
                    g.drawImage(fish_array_blue[i].img, fish_array_blue[i].x_pos, fish_array_blue[i].y_pos, 60, 60, this);
                    //g.drawRect(fish_array_blue[i].x_pos, fish_array_blue[i].y_pos, 60, 60);
                }
            }

            Graphics2D g2 = (Graphics2D) g;
            BasicStroke stroke = new BasicStroke(2);
            g2.setStroke(stroke);

            //Draw the rising bubble
            for (int i = 0; i < bubble_string.leap; i++) {
                BasicStroke stroke1 = new BasicStroke(3);
                g2.setStroke(stroke1);
                g2.setColor(new Color(255, 255, 255, 180));
                g2.fillOval(bubble_string.x_pos[i], bubble_string.y_pos[i], bubble_string.diameter[i], bubble_string.diameter[i]);
                g2.setColor(new Color(232, 232, 232));
                g2.drawOval(bubble_string.x_pos[i], bubble_string.y_pos[i], bubble_string.diameter[i], bubble_string.diameter[i]);
                BasicStroke stroke2 = new BasicStroke(2);
                g2.setStroke(stroke2);
                g2.setColor(new Color(254, 253, 255));
                g2.drawArc(bubble_string.x_pos[i] + (bubble_string.diameter[i] / 8),
                        bubble_string.y_pos[i] + (bubble_string.diameter[i] / 8),
                        bubble_string.diameter[i], bubble_string.diameter[i], 118, 50);
            }

            //Surface [I] and simulate shading
            g2.setColor(Color.BLACK);
            int[] xPoints1 = {320, 880, 880, 320};
            int[] yPoints1 = {600, 560, 200, 240};
            g2.drawPolygon(xPoints1, yPoints1, 4);
            g.setColor(new Color(134, 132, 135));
            g.drawLine(882, 200, 882, 560);
            g.setColor(new Color(70, 100, 82));
            g.drawLine(318, 600, 318, 240);

            //Surface [II] and simulate shading
            g2.setColor(Color.BLACK);
            int[] xPoints2 = {320, 200, 200, 320};
            int[] yPoints2 = {240, 160, 520, 600};
            g2.drawPolygon(xPoints2, yPoints2, 4);
            g.setColor(new Color(134, 132, 135));
            g.drawLine(198, 160, 198, 520);

            //Base [III] and Gradient fill
            int[] xPoints3 = {316, 192, 192, 316};
            int[] yPoints3 = {596, 512, 552, 636};
            GradientPaint paint = new GradientPaint(192, 520, new Color(66, 66, 68), 244, 596, new Color(95, 95, 98), true);
            g2.setPaint(paint);
            g2.fillPolygon(xPoints3, yPoints3, 4);
            g.setColor(new Color(134, 132, 135));
            g.drawLine(196, 516, 316, 596);

            //Base [IV] and Gradient fill
            g.setColor(new Color(8, 5, 20));
            int[] xPoints4 = {316, 888, 888, 316};
            int[] yPoints4 = {596, 554, 590, 636};
            GradientPaint paint_ = new GradientPaint(316, 596, new Color(8, 5, 20), 888, 600, new Color(6, 5, 10), true);
            g2.setPaint(paint_);
            g2.fillPolygon(xPoints4, yPoints4, 4);
            g.setColor(new Color(134, 132, 135));
            g.drawLine(400, 590, 888, 554);

            //Top Eage [V] and Gradient fill
            int[] xPoints5 = {324, 192, 192, 324};
            int[] yPoints5 = {208, 122, 162, 250};
            GradientPaint paint1 = new GradientPaint(192, 122, new Color(66, 66, 68), 244, 240, new Color(95, 95, 98), true);
            g2.setPaint(paint1);
            g2.fillPolygon(xPoints5, yPoints5, 4);

            //Top Eage [VI] and Gradient fill
            g.setColor(new Color(8, 5, 20));
            int[] xPoints6 = {324, 888, 888, 324};
            int[] yPoints6 = {208, 180, 216, 250}; //CHANGE TWO INDEX! FIRST AND LAST
            GradientPaint paint2 = new GradientPaint(162, 110, new Color(59, 59, 61), 444, 108, new Color(19, 18, 23), true);
            g2.setPaint(paint2);
            g2.fillPolygon(xPoints6, yPoints6, 4);

            //Top Surface [VII] and Gradient fill
            int[] xPoints7 = {200, 220, 328, 328};
            int[] yPoints7 = {124, 80, 142, 208};
            GradientPaint paint3 = new GradientPaint(192, 520, new Color(66, 66, 68), 244, 596, new Color(95, 95, 98), true);
            g2.setPaint(paint3);
            g2.fillPolygon(xPoints7, yPoints7, 4);
            g.setColor(new Color(120, 120, 120));
            g.drawLine(193, 122, 324, 205);

            //the shading between [V] and [VII]
            g.setColor(new Color(120, 120, 120));
            g.drawLine(204, 127, 324, 208);

            //Top Surface [VIII] and Gradient fill
            int[] xPoints8 = {328, 846, 888, 328};
            int[] yPoints8 = {142, 124, 176, 206};
            GradientPaint paint4 = new GradientPaint(324, 220, new Color(59, 59, 61), 888, 216, new Color(19, 18, 23), true);
            g2.setPaint(paint4);
            g2.fillPolygon(xPoints8, yPoints8, 4);
            g.setColor(new Color(120, 120, 120));
            g.drawLine(326, 206, 888, 178);

            //the shading between [VI] and [VIII]
            g.setColor(new Color(120, 120, 120));
            g.drawLine(326, 206, 888, 180);

            //Top Surface
            int[] xPoints9 = {220, 328, 846, 666};
            int[] yPoints9 = {80, 142, 124, 66};
            GradientPaint paint5 = new GradientPaint(328, 142, new Color(132, 130, 128), 700, 70, new Color(90, 95, 95), true);
            g2.setPaint(paint5);
            g2.fillPolygon(xPoints9, yPoints9, 4);
            g.setColor(new Color(120, 120, 120));
            g.drawLine(322, 207, 888, 180);
            g.setColor(new Color(120, 120, 120));
            g.drawLine(322, 207, 888, 177);

            //Draw the cat knight and its Hook
            g.drawImage(new ImageIcon("cat_knight.png").getImage(), catk.x_pos, catk.y_pos, 90, 135, this);
            g.drawImage(new ImageIcon("hook.png").getImage(), catk.x_pos_hook, catk.y_pos_hook, 30, 70, this);

            //g.drawRect(catk.x_pos_hook, catk.y_pos_hook + 40, 30, 30);
            
            //Press guide and button prompt effects
            g.setFont(new Font("Font.BOLD", 1, 35));
            for (int i = 0; i < 3; i++) {
                g.setColor(new Color(211, 211, 211));
                if (pguide.KeyArray[i].concave == false) {
                    g.fill3DRect(pguide.KeyArray[i].x_pos, pguide.KeyArray[i].y_pos, 90, 70, down);
                } else {
                    g.fill3DRect(pguide.KeyArray[i].x_pos, pguide.KeyArray[i].y_pos, 90, 70, up);
                }
                g.setColor(Color.BLACK);
                g.drawString(pguide.KeyArray[i].content, pguide.KeyArray[i].x_pos + 1, pguide.KeyArray[i].y_pos + 50);
            }
            
            //the comment next to the button
            g.setFont(new Font(Font.DIALOG, 0, 15));
            g.setColor(Color.BLACK);
            g.drawString("Cat move towards left", 1042, 170);
            g.drawString("Cat move towards right", 1042, 270);
            g.drawString("Put down the hook", 1042, 370);

        }
    }

    public class Cat extends Thread {

        int x_pos;
        int y_pos;
        int x_pos_hook;
        int y_pos_hook;
        final int hook_speed = 7;
        final int step = 15;
        boolean lock = false;
        int run_code = 0;

        //constructor to assign initial value
        public Cat() {
            x_pos = 500;
            y_pos = 0;
            x_pos_hook = x_pos;
            y_pos_hook = y_pos + 240;
        }

        public void run() {
            while (true) {
                if (!(lock)) {
                    myEvent();
                    try {
                        Thread.sleep(300);
                    } catch (Exception exc) {
                        System.out.println("Exception is caught");
                    }
                    if (run_code == 1) {
                        x_pos -= step;
                        x_pos_hook = x_pos;
                        run_code = 0;
                    } else if (run_code == 2) {
                        x_pos += step;
                        x_pos_hook = x_pos;
                        run_code = 0;
                    }
                    frame.repaint();
                }

                if (lock) {
                    while (y_pos_hook <= 424) { //494[end of fish appearing area] - 70
                        y_pos_hook += hook_speed;
                        try {
                            Thread.sleep(100);
                        } catch (Exception exc) {
                            System.out.println("Exception is caught");
                            frame.repaint();
                        }
                    }
                    while (y_pos_hook > 240) { //494[end of fish appearing area] - 70
                        y_pos_hook -= hook_speed;
                        try {
                            Thread.sleep(50);
                        } catch (Exception exc) {
                            System.out.println("Exception is caught");
                            frame.repaint();
                        }
                    }
                    try {
                        Thread.sleep(300);
                    } catch (Exception exc) {
                        System.out.println("Exception is caught");
                    }
                    lock = false;
                }

            }
        }

        private void myEvent() {
            frame.addKeyListener(new KeyAdapter()//键盘监听按钮
            {
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//                        x_pos-=step;
//                        x_pos_hook = x_pos;
                        run_code = 1;
                        pguide.KeyArray[0].concave = true;
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//                        x_pos+=step;
//                        x_pos_hook = x_pos;
                        run_code = 2;
                        pguide.KeyArray[1].concave = true;
                        e.consume();
                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        pguide.KeyArray[2].concave = true;
                        lock = true;
                    } else {
                        e.consume();
                    }
                }

            });
        }
    }

   
    public class Collision extends Thread {

        int hook_up;
        int hook_bottom;
        int hook_right;
        int hook_left;

        int[] fish_up = new int[4];
        int[] fish_bottom = new int[4];
        int[] fish_right = new int[4];
        int[] fish_left = new int[4];
        
        /**
         * This function assign the boundary of hook
         * and check boundary of each fish, to determine whether to collide
         */
        public void run() {
            while (true) {
                hook_left = catk.x_pos_hook;
                hook_right = catk.x_pos_hook + 30;
                hook_up = catk.y_pos_hook + 40;
                hook_bottom = catk.y_pos_hook + 70;
                //System.out.println("Position1");
                for (int i = 0; i < 4; i++) {
                    fish_left[i] = fish_array[i].x_pos;
                    fish_right[i] = fish_array[i].x_pos + 60;
                    fish_up[i] = fish_array[i].y_pos;
                    fish_bottom[i] = fish_array[i].y_pos + 60;
                    //System.out.println("Position2");
                    if (fish_left[i] < hook_right && fish_right[i] > hook_left && fish_up[i] < hook_bottom && fish_bottom[i] > hook_up) {
                        fish_array[i].is_impact = true;
                        lost[i] = true;
                        //System.out.println("is impact??" +i+ fish_array[i].is_impact);
                    }
                }

                for (int i = 0; i < 4; i++) {
                    fish_left[i] = fish_array_blue[i].x_pos;
                    fish_right[i] = fish_array_blue[i].x_pos + 60;
                    fish_up[i] = fish_array_blue[i].y_pos;
                    fish_bottom[i] = fish_array_blue[i].y_pos + 60;
                    //System.out.println("Position3");
                    if (fish_left[i] < hook_right && fish_right[i] > hook_left && fish_up[i] < hook_bottom && fish_bottom[i] < hook_up) {
                        fish_array_blue[i].is_impact = true;
                        lost_blue[i] = true;
                        //System.out.println("is impact??" +i+ fish_array[i].is_impact);
                    }
                }
            }
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
     * @param bufferedimage Target Image
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
