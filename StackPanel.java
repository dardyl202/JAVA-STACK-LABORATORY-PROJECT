/* Description: Custom Swing panel that draws the stack as vertical blocks.
   The newest element is drawn at the top and marked with "TOP". Unused slots
   are drawn as dashed outlines. This class only DRAWS a snapshot it is given.

   Programmed by: <TABAT, KATAG, TINGAL, PUNDOGAR, BARNES> <BSIT2> <cn48143> <DATA STRUCTURE>

   Last Modified: <10-20-26>

   Version: 1.0

   Acknowledgements: <We used claude Ai to help enhanced our code/program, and we ask for advice to make our code more presentable. Also we ask Ai to find some error in our code that we can't find like for example, syntax error or logical error.>
*/

import javax.swing.*;
import java.awt.*;

public class StackPanel extends JPanel {

    private static final Color BLOCK = new Color(66, 133, 244);
    private static final Color TOP_BLOCK = new Color(244, 126, 32);
    private static final Color PEEK_BLOCK = new Color(255, 214, 10);
    private static final Color BACKGROUND = new Color(238, 242, 246);

    private int[] contents = new int[0];
    private int capacity;
    private boolean peekHighlight = false;

    public StackPanel(int capacity) {
        this.capacity = capacity;
        setPreferredSize(new Dimension(300, 400));
        setBackground(BACKGROUND);
    }

    /** Gives the panel a new snapshot (bottom -> top) and repaints. */
    public void showStack(int[] snapshot, boolean peek) {
        this.contents = snapshot;
        this.peekHighlight = peek;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int margin = 20, gap = 6;
        int left = 50, right = 70;
        int slotH = (getHeight() - 2 * margin - (capacity - 1) * gap) / capacity;
        int w = getWidth() - left - right;

        for (int i = 0; i < capacity; i++) {
            int y = getHeight() - margin - (i + 1) * slotH - i * gap;

            // index label on the left
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.drawString("[" + i + "]", 10, y + slotH / 2 + 4);

            if (i < contents.length) {
                boolean isTop = (i == contents.length - 1);
                g2.setColor(isTop ? (peekHighlight ? PEEK_BLOCK : TOP_BLOCK) : BLOCK);
                g2.fillRoundRect(left, y, w, slotH, 12, 12);
                g2.setColor(Color.DARK_GRAY);
                g2.drawRoundRect(left, y, w, slotH, 12, 12);

                g2.setColor(isTop && peekHighlight ? Color.BLACK : Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 18));
                String text = String.valueOf(contents[i]);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(text, left + (w - fm.stringWidth(text)) / 2,
                        y + (slotH + fm.getAscent() - fm.getDescent()) / 2);

                if (isTop) {
                    g2.setColor(new Color(200, 80, 0));
                    g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                    g2.drawString("<- TOP", left + w + 8, y + slotH / 2 + 5);
                }
            } else {
                g2.setColor(Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                        10f, new float[]{5f, 5f}, 0f));
                g2.drawRoundRect(left, y, w, slotH, 12, 12);
                g2.setStroke(new BasicStroke(1f));
            }
        }
        g2.dispose();
    }
}
