import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class Application extends JPanel {

    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;
    private static final int LENGTH = 100;
    private double hoursAngle,minuteAngle,secondAngle,pendulumAngle;
    private Application() {
        this.pendulumAngle = Math.PI/6;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D)g;
        drawPendulum(graphics2D);
        drawMinutes(graphics2D);
        drawSeconds(graphics2D);
        drawHours(graphics2D);
        graphics2D.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        g.drawOval(WIDTH/2-LENGTH,HEIGHT/2-LENGTH,LENGTH*2,LENGTH*2);
    }

    private void drawHours(Graphics2D graphics2D) {
        int X1 = WIDTH/2;
        int Y1 = HEIGHT/2;

        int X2 = X1 + (int)(Math.sin(hoursAngle)*LENGTH*0.4);
        int Y2 = Y1 + (int)(Math.cos(hoursAngle)*LENGTH*0.4);

        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(Color.RED);

        graphics2D.drawLine(X1,Y1,X2,Y2);
    }

    private void drawSeconds(Graphics2D graphics2D) {
        int X1 = WIDTH/2;
        int Y1 = HEIGHT/2;

        int X2 = X1 + (int)(Math.sin(secondAngle)*LENGTH*0.7);
        int Y2 = Y1 + (int)(Math.cos(secondAngle)*LENGTH*0.7);

        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(Color.GREEN);

        graphics2D.drawLine(X1,Y1,X2,Y2);
    }

    private void drawMinutes(Graphics2D graphics2D) {
        int X1 = WIDTH/2;
        int Y1 = HEIGHT/2;

        int X2 = X1 + (int)(Math.sin(minuteAngle)*LENGTH*0.6);
        int Y2 = Y1 + (int)(Math.cos(minuteAngle)*LENGTH*0.6);

        graphics2D.setStroke(new BasicStroke(4));
        graphics2D.setColor(Color.BLUE);

        graphics2D.drawLine(X1,Y1,X2,Y2);

    }

    private void drawPendulum(Graphics2D graphics2D) {
        int X1 = WIDTH/2;
        int Y1 = HEIGHT/2+100;

        int X2 = X1 + (int)(Math.sin(pendulumAngle)*LENGTH*1.2);
        int Y2 = Y1 + (int)(Math.cos(pendulumAngle)*LENGTH*1.2);
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.setColor(Color.BLACK);

        graphics2D.drawLine(X1,Y1,X2,Y2);
        graphics2D.fillOval(X2-7,Y2-7,14,14);

    }


    public static void main(String[] args) {
        Application application = new Application();
        JFrame frame = new JFrame("Flower");
        frame.add(application);
        frame.setSize(720,480);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        application.drawClock();
    }

    private void drawClock() {

        double angularVelocity = 0;
        double DT = 0.1;
        while (true) {

            Calendar calendar = Calendar.getInstance();
            int hours = calendar.get(Calendar.HOUR);
            int seconds = calendar.get(Calendar.SECOND);
            int minutes = calendar.get(Calendar.MINUTE);
            minuteAngle = Math.PI-Math.toRadians(minutes*6);
            secondAngle = Math.PI-Math.toRadians(seconds*6);
            hoursAngle = Math.PI-Math.toRadians(hours*6*5);

            angularVelocity += -9.81*Math.sin(pendulumAngle)/LENGTH*DT;
            pendulumAngle += angularVelocity*DT;
            repaint();
            try{ Thread.sleep(10); }
            catch (InterruptedException e){}
        }
    }


}
