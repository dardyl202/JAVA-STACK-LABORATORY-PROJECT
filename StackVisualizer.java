/* Description: Swing GUI for the 2D Stack Visualizer. Receives user input, calls
   the methods of ArrayStack (push, pop, peek, isEmpty, clear, size) and redraws
   the stack after every operation. The GUI never touches the stack's internal array.

   Programmed by: <TABAT, KATAG, TINGAL, PUNDOGAR, BARNES> <BSIT2> <CN48143> <DATA STRUCTURE>

   Last Modified: <10-20-26>

   Version: 1.0

   Acknowledgements: <We used claude Ai to help enhanced our code/program, and we ask for advice to make our code more presentable. Also we ask Ai to find some error in our code that we can't find like for example, syntax error or logical error.>
*/

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StackVisualizer extends JFrame {

    private static final Color OK = new Color(0, 128, 0);
    private static final Color ERROR = new Color(192, 0, 0);

    private final ArrayStack stack = new ArrayStack(ArrayStack.MAX_CAPACITY);
    private final Random random = new Random();

    private final StackPanel stackPanel = new StackPanel(ArrayStack.MAX_CAPACITY);
    private final JTextField inputField = new JTextField(8);
    private final JLabel sizeLabel = new JLabel();
    private final JLabel statusLabel = new JLabel(" ");

    public StackVisualizer() {
        super("2D Stack Visualizer (Array-Based, Capacity " + ArrayStack.MAX_CAPACITY + ")");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(stackPanel, BorderLayout.CENTER);
        add(buildControlPanel(), BorderLayout.EAST);

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(statusLabel, BorderLayout.SOUTH);

        refresh(false);
        setStatus("Ready. Enter a value and press PUSH.", OK);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 15));

        panel.add(new JLabel("Enter value:"));
        panel.add(inputField);

        JButton pushBtn = new JButton("PUSH");
        JButton popBtn = new JButton("POP");
        JButton peekBtn = new JButton("PEEK");
        JButton clearBtn = new JButton("CLEAR");
        JButton checkBtn = new JButton("CHECK STACK");
        JButton randomBtn = new JButton("RANDOM");

        pushBtn.addActionListener(e -> doPush());
        inputField.addActionListener(e -> doPush());   // Enter key also pushes
        popBtn.addActionListener(e -> doPop());
        peekBtn.addActionListener(e -> doPeek());
        clearBtn.addActionListener(e -> doClear());
        checkBtn.addActionListener(e -> doCheck());
        randomBtn.addActionListener(e -> doRandom());

        panel.add(pushBtn);
        panel.add(popBtn);
        panel.add(peekBtn);
        panel.add(clearBtn);
        panel.add(checkBtn);
        panel.add(randomBtn);

        sizeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(sizeLabel);
        return panel;
    }

    // ---------- operations: GUI -> stack methods ----------

    private void doPush() {
        String text = inputField.getText().trim();
        int value;
        try {
            value = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            setStatus("INVALID INPUT: Please enter a whole number", ERROR);
            return;
        }
        try {
            stack.push(value);
            refresh(false);
            setStatus("PUSH: " + value + " added to the top", OK);
            inputField.setText("");
        } catch (IllegalStateException ex) {
            setStatus(ex.getMessage(), ERROR);
        }
        inputField.requestFocusInWindow();
    }

    private void doPop() {
        try {
            int removed = stack.pop();
            refresh(false);
            setStatus("POP: " + removed + " removed from the top", OK);
        } catch (IllegalStateException ex) {
            setStatus(ex.getMessage(), ERROR);
        }
    }

    private void doPeek() {
        try {
            int top = stack.peek();
            refresh(true);   // highlight only, nothing is removed or modified
            setStatus("PEEK: Top element is " + top, OK);
        } catch (IllegalStateException ex) {
            setStatus(ex.getMessage(), ERROR);
        }
    }

    private void doClear() {
        stack.clear();
        refresh(false);
        setStatus("CLEAR: Stack is now empty", OK);
    }

    private void doCheck() {
        if (stack.isEmpty()) {
            setStatus("CHECK STACK: The stack is EMPTY", OK);
        } else if (stack.isFull()) {
            setStatus("CHECK STACK: The stack is NOT empty (it is FULL, " + stack.size() + " elements)", OK);
        } else {
            setStatus("CHECK STACK: The stack is NOT empty (" + stack.size() + " elements)", OK);
        }
    }

    private void doRandom() {
        stack.clear();
        int count = random.nextInt(stack.capacity()) + 1;      // 1..capacity
        for (int i = 0; i < count; i++) {
            stack.push(random.nextInt(100) + 1);               // 1..100
        }
        refresh(false);
        setStatus("RANDOM: Stack reset with " + count + " random element(s)", OK);
    }

    // ---------- helpers ----------

    /** Redraws the stack from the stack's current state. */
    private void refresh(boolean peekHighlight) {
        stackPanel.showStack(stack.toArray(), peekHighlight);
        sizeLabel.setText("Size: " + stack.size() + " / " + stack.capacity());
    }

    private void setStatus(String message, Color color) {
        statusLabel.setForeground(color);
        statusLabel.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StackVisualizer().setVisible(true));
    }
}
