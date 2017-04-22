import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Stack;

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
    private boolean polygonDrawn;
    private static int RED = Color.RED.getRGB();
    private static int GREEN = Color.GREEN.getRGB();

    Application(){
        mVertices = new ArrayList<>();
        mCanvas = new BufferedImage(720,480,BufferedImage.TYPE_3BYTE_BGR);
        polygonDrawn = false;
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.drawImage(mCanvas,0,0,this);
    }



    private void drawPolygon() {
        if(mVertices.size() >= 3) {
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

    private void fillPolygon(int x, int y) {
        Stack<Point> callStack = new Stack<>();
        callStack.push(new Point(x,y));
        while(!callStack.empty()){
            Point current = callStack.pop();
            if(isInside(current.X,current.Y) && mCanvas.getRGB(current.X,current.Y) != GREEN) {
                callStack.push(new Point(current.X+1,current.Y));
                callStack.push(new Point(current.X-1,current.Y));
                callStack.push(new Point(current.X,current.Y+1));
                callStack.push(new Point(current.X,current.Y-1));
                mCanvas.setRGB(current.X,current.Y,GREEN);
                repaint();
            }
        }
    }

    private boolean isInside(int x, int y) {
        boolean result = false;
        int i,j;
        for(i = 0, j = mVertices.size()-1; i < mVertices.size(); j = i++){
            if((mVertices.get(i).Y > y) != (mVertices.get(j).Y > y) &&
                    x < (mVertices.get(j).X - mVertices.get(i).X)
                            *(y- mVertices.get(i).Y)/(mVertices.get(j).Y - mVertices.get(i).Y)
                    + mVertices.get(i).X)
                result = !result;
        }
        return result;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1) {
            mVertices.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            mCanvas.setRGB(mouseEvent.getX(),mouseEvent.getY(),GREEN);
            repaint();
        }
        else if(mouseEvent.getButton() == MouseEvent.BUTTON2) {
            polygonDrawn = true;
            drawPolygon();
        }
        else if(mouseEvent.getButton() == MouseEvent.BUTTON3 && polygonDrawn) {
            polygonDrawn = false;
            fillPolygon(mouseEvent.getX(), mouseEvent.getY());
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
