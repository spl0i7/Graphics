import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Application extends JPanel implements MouseListener {


    public static void main(String[] args) {
        Application scanlineApp = new Application();
        JFrame frame = new JFrame("Scan Line");
        frame.add(scanlineApp);
        frame.setSize(720,480);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private BufferedImage mCanvas;
    private ArrayList<Point> mVertices;

    private static int GREEN = Color.GREEN.getRGB();

    private static int MAX_X = 600;
    private static int MIN_X = 100;
    private static int MAX_Y = 400;
    private static int MIN_Y = 100;

    private static final int OUT_LEFT   = 1;
    private static final int OUT_ABOVE  = 2;
    private static final int OUT_RIGHT  = 4;
    private static final int OUT_BOTTOM = 8;
    //BRAL

    static int outCode(int X,int Y) {
        int code = 0;
        if(X > MAX_X) code |= OUT_RIGHT;
        if(X < MIN_X) code |= OUT_LEFT;
        if(Y > MAX_Y) code |= OUT_BOTTOM;
        if(Y < MIN_Y) code |= OUT_ABOVE;
        return code;
    }

    Application(){
        mVertices = new ArrayList<>();
        mCanvas = new BufferedImage(720,480,BufferedImage.TYPE_3BYTE_BGR);
        addMouseListener(this);
    }


    @Override
    public void paintComponent(Graphics graphics) {
        drawWindow();
        graphics.drawImage(mCanvas,0,0,this);

    }

    private void drawWindow() {
        Graphics2D graphics2D = mCanvas.createGraphics();
        graphics2D.setColor(Color.YELLOW);
        graphics2D.drawLine(MIN_X,MIN_Y,MAX_X,MIN_Y);
        graphics2D.drawLine(MIN_X,MIN_Y,MIN_X,MAX_Y);
        graphics2D.drawLine(MAX_X,MIN_Y,MAX_X,MAX_Y);
        graphics2D.drawLine(MIN_X,MAX_Y,MAX_X,MAX_Y);
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1) {
            mVertices.add(new Point(mouseEvent.getX(),mouseEvent.getY()));
            mCanvas.setRGB(mouseEvent.getX(),mouseEvent.getY(),GREEN);
            repaint();
        }
        else if(mouseEvent.getButton() == MouseEvent.BUTTON2) {
            drawPolygon();
        }
        else if(mouseEvent.getButton() == MouseEvent.BUTTON3){
            clipPolygon();
            clearImage();
            clipPolygon();
        }
    }

    private void clearImage() {
        Graphics2D graphics2D = mCanvas.createGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0,0,720,480);
        repaint();
    }
    private void clipAndDraw(int X1, int Y1, int X2, int Y2){
        int f1 = outCode(X1,Y1);
        int f2 = outCode(X2,Y2);


        while((f1|f2) != 0) {
            if ((f1 & f2) != 0) {
                return;
            }

            int dx = X2 - X1;
            int dy = Y2 - Y1;

            if (f1 != 0) {
                if((f1&OUT_LEFT) == OUT_LEFT && dx != 0) {
                    Y1 += (MIN_X-X1)*dy/dx;
                    X1 = MIN_X;

                }
                else if((f1&OUT_RIGHT) == OUT_RIGHT && dx != 0) {
                    Y1 += (MAX_X-X1)*dy/dx;
                    X1 = MAX_X;
                }
                else if((f1&OUT_BOTTOM) == OUT_BOTTOM && dy != 0) {
                    X1 += (MAX_Y - Y1)*dx/dy;
                    Y1 = MAX_Y;
                }
                else if((f1&OUT_ABOVE) == OUT_ABOVE && dy != 0) {
                    X1 += (MIN_Y - Y1)*dx/dy;
                    Y1 = MIN_Y;
                }

                f1 = outCode(X1,Y1);
            }
            if (f2 != 0) {
                if((f2&OUT_LEFT) == OUT_LEFT && dx != 0) {
                    Y2 += (MIN_X-X2)*dy/dx;
                    X2 = MIN_X;

                }
                else if((f2&OUT_RIGHT) == OUT_RIGHT && dx != 0) {
                    Y2 += (MAX_X-X2)*dy/dx;
                    X2 = MAX_X;
                }
                else if((f2&OUT_BOTTOM) == OUT_BOTTOM && dy != 0) {
                    X2 += (MAX_Y - Y2)*dx/dy;
                    Y2 = MAX_Y;
                }
                else if((f2&OUT_ABOVE) == OUT_ABOVE && dy != 0) {
                    X2 += (MIN_Y - Y2)*dx/dy;
                    Y2 = MIN_Y;
                }
                f2 = outCode(X2,Y2);
            }
        }

        Graphics2D graphics2D = mCanvas.createGraphics();
        graphics2D.setColor(Color.GREEN);
        graphics2D.drawLine(X1,Y1,X2,Y2);
    }

    private void clipPolygon() {
        Graphics2D graphics2D = mCanvas.createGraphics();
        graphics2D.setColor(Color.RED);
        for (int i = 0; i  < mVertices.size()-1; ++i) {
            Point current = mVertices.get(i);
            Point next = mVertices.get(i+1);
            clipAndDraw(current.X,current.Y,next.X,next.Y);
        }
        Point  first = mVertices.get(0);
        Point  last = mVertices.get(mVertices.size()-1);
        clipAndDraw(first.X,first.Y,last.X,last.Y);
        repaint();
    }
    private void drawPolygon() {
        if(mVertices.size() >=3) {
            Graphics2D graphics2D = mCanvas.createGraphics();
            graphics2D.setColor(Color.RED);
            for (int i = 0; i  < mVertices.size()-1; ++i) {
                Point current = mVertices.get(i);
                Point next = mVertices.get(i+1);
                graphics2D.drawLine(current.X,current.Y,next.X,next.Y);
            }
            Point  first = mVertices.get(0);
            Point  last = mVertices.get(mVertices.size()-1);
            graphics2D.drawLine(first.X,first.Y,last.X,last.Y);
            repaint();
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}
