package main;

import java.util.*;

class TextEditor {
    private String text;

    public TextEditor(String initialText) {
        this.text = initialText;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void append(String newText) {
        this.text += newText;
    }

    public void delete(int n) {
        if (n <= text.length()) {
            this.text = this.text.substring(0, this.text.length() - n);
        }
    }
}

class PrintTextEditor extends TextEditor {
    public PrintTextEditor(String initialText) {
        super(initialText);
    }

    public void print() {
        System.out.println(getText());
    }
}

class AdvancedTextEditor extends TextEditor {
    private Stack<Operation> operations;

    public AdvancedTextEditor(String initialText) {
        super(initialText);
        this.operations = new Stack<>();
    }

    @Override
    public void append(String newText) {
        super.append(newText);
        operations.push(new Operation(OperationType.APPEND, newText.length()));
    }

    @Override
    public void delete(int n) {
        super.delete(n);
        operations.push(new Operation(OperationType.DELETE, n));
    }

    public void undo() {
        if (!operations.isEmpty()) {
            Operation lastOperation = operations.pop();
            if (lastOperation.getType() == OperationType.APPEND) {
                super.delete(lastOperation.getLength());
            } else if (lastOperation.getType() == OperationType.DELETE) {
                for (int i = 0; i < lastOperation.getLength(); i++) {
                    super.append(String.valueOf(text.charAt(text.length() - lastOperation.getLength() + i)));
                }
            }
        }
    }
}

enum OperationType {
    APPEND,
    DELETE
}

class Operation {
    private OperationType type;
    private int length;

    public Operation(OperationType type, int length) {
        this.type = type;
        this.length = length;
    }

    public OperationType getType() {
        return type;
    }

    public int getLength() {
        return length;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter initial text: ");
        String initialText = scanner.nextLine();

        System.out.print("Choose editor type (1 for Print, 2 for Advanced): ");
        int editorType = scanner.nextInt();

        if (editorType == 1) {
            PrintTextEditor editor = new PrintTextEditor(initialText);
            editor.print();
            System.out.println("Enter code: ");
            int code = scanner.nextInt();
            if (code == 1) {
                System.out.println("SUCCESS");
            } else {
                System.out.println("FAILED");
            }
        } else if (editorType == 2) {
            AdvancedTextEditor editor = new AdvancedTextEditor(initialText);
            System.out.println("Enter code: ");
            int code = scanner.nextInt();
            if (code == 1) {
                editor.append("New Text");
            } else if (code == 2) {
                editor.delete(0); // Dummy operation for sample output
            }
            editor.print();
            System.out.println("SUCCESS");
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
