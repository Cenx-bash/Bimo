import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bimo extends JFrame {

    private final JTextArea chatArea;
    private final JTextField inputField;
    private final BimoEngine bot;

    public Bimo() {
        setTitle("NovaBot");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Caveat", Font.PLAIN, 18));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(inputField, BorderLayout.SOUTH);

        bot = new BimoEngine("responses.json");

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUserInput();
            }
        });

        appendChat("Bimo: Hello! I'm  Bimo. Ask me anything or type 'bye' to exit.");
    }

    private void handleUserInput() {
        String input = inputField.getText();
        if (input.isEmpty()) return;

        appendChat("You: " + input);
        bot.logInput(input);

        if (input.equalsIgnoreCase("bye")) {
            appendChat("Bimo: " + bot.getResponse("bye"));
            System.exit(0);
        } else {
            String response = bot.getResponse(input);
            appendChat("NovaBot: " + response);
        }

        inputField.setText("");
    }

    private void appendChat(String message) {
        chatArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Bimo app = new Bimo();
            app.setVisible(true);
        });
    }
}
