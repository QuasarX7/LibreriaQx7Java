/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.quasar_x7.java.Console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;


/**
 *
 * @author Ninja
 */
public class OutputConsole extends JTextArea {
    private PrintStream printStream;
    
    public OutputConsole(){
        printStream = new PrintStream(new ConsolePrintStream());
    }
    
     
    public PrintStream getPrintStream() {
        return printStream;
    }
    
    //L' output stream definito da noi
    private class ConsolePrintStream extends ByteArrayOutputStream {
        @Override
        public synchronized void write(byte[] b, int off, int len) {
            setCaretPosition(getDocument().getLength());
            String str = new String(b);
            append(str.substring(off, len));
        }
    }
}

