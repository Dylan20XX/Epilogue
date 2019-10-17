package inputs;

import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import utils.UIManager;

/*
 * class for mouse control
 * 
 * uses the MouseLisenter and MouseMotionListener
 */
public class MouseManager implements MouseListener, MouseMotionListener {

        private boolean leftPressed, rightPressed;
        private boolean leftClicked, rightClicked;
        private int mouseX, mouseY;
        private UIManager uiManager;

        // empty constructor, nothing needs to be done right now
        public MouseManager() {

        }

        // this method enables a uiManager to use the mouse control
        public void setUIManager(UIManager uiManager) {
                this.uiManager = uiManager;
        }
        
        public void removeUIManager(UIManager uiManager) {
                this.uiManager = null;
        }

        // Override methods
        @Override
        public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Button1, left mouse
                        leftClicked = true;
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Button3, right mouse
                        rightClicked = true;
                }
        }

        @Override
        public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Button1, left mouse
                        leftPressed = true;
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Button3, right mouse
                        rightPressed = true;
                }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) { // Button1, left mouse
                        leftPressed = false;
                } else if (e.getButton() == MouseEvent.BUTTON3) { // Button3, right mouse
                        rightPressed = false;
                }

                if (uiManager != null)
                        uiManager.onMouseRelease(e);

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
                // detects the position of the mouse
                mouseX = e.getX();
                mouseY = e.getY();

                if (uiManager != null)
                        uiManager.onMouseMove(e);

        }

        public Rectangle mouseBound() {
                return new Rectangle(mouseX, mouseY, 5, 5);
        }

        public boolean isLeftPressed() {
                return leftPressed;
        }

        public void setLeftPressed(boolean leftPressed) {
                this.leftPressed = leftPressed;
        }

        public boolean isRightPressed() {
                return rightPressed;
        }

        public void setRightPressed(boolean rightPressed) {
                this.rightPressed = rightPressed;
        }
        
        public boolean isLeftClicked() {
                if(leftClicked) {
                        leftClicked = false;
                        return true;
                }
                return false;
        }
        
        public boolean isRightClicked() {
                if(rightClicked) {
                        rightClicked = false;
                        return true;
                }
                return false;
        }
        public int getMouseX() {
                return MouseInfo.getPointerInfo().getLocation().x;
        }
        
        public int getMouseClickX() {
                return mouseX;
        }

        public void setMouseX(int mouseX) {
                this.mouseX = mouseX;
        }

        public int getMouseY() {
                return MouseInfo.getPointerInfo().getLocation().y;
        }

        public void setMouseY(int mouseY) {
                this.mouseY = mouseY;
        }

        public UIManager getUiManager() {
                return uiManager;
        }

        public void setUiManager(UIManager uiManager) {
                this.uiManager = uiManager;
        }

}
