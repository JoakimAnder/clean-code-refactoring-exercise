package refactored2.window.text;

import refactored2.actions.Action;

import javax.swing.text.JTextComponent;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FunctionalTextBox implements TextBox {
    private Supplier<String> getText;
    private Consumer<String> set;
    private Consumer<String> append;
    private Action clear;

    public FunctionalTextBox(JTextComponent field) {
        getText = field::getText;
        set = field::setText;
        append = text -> field.setText(field.getText().concat(text));
        clear = () -> field.setText("");
    }

    public FunctionalTextBox(Supplier<String> getText, Consumer<String> set, Consumer<String> append, Action clear) {
        this.getText = getText;
        this.set = set;
        this.append = append;
        this.clear = clear;
    }

    @Override
    public String getText() {
        return getText.get();
    }

    @Override
    public void set(String text) {
        set.accept(text);
    }

    @Override
    public void append(String text) {
        append.accept(text);
    }

    @Override
    public void clear() {
        clear.execute();
    }
}
